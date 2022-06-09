package com.bibabo.bibabostockservice.services.impl;

import com.bibabo.bibabostockservice.domain.StockInventory;
import com.bibabo.bibabostockservice.model.enums.RedisPrefixEnum;
import com.bibabo.bibabostockservice.services.StockInventoryService;
import com.bibabo.stock.dto.OccupyStockResponseDTO;
import com.bibabo.stock.dto.StockReqeustDTO;
import com.bibabo.stock.dto.StockResponseDTO;
import com.bibabo.stock.services.StockServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 15:35
 * @description
 */
@Slf4j
@DubboService
public class StockServiceDbImpl implements StockServiceI {

    @Autowired
    private StockInventoryService stockInventoryService;

    @Autowired
    RedissonClient redissonClient;


    @Override
    public OccupyStockResponseDTO occupyStock(List<StockReqeustDTO> reqeustDTOList) {
        String errorMsg = null;

        // 预防死锁，按照skuId升序排序，依次加锁
        reqeustDTOList.sort((o1, o2) -> (int) (o1.getSkuId() - o2.getSkuId()));

        List<RLock> writeLockList = new LinkedList<>();
        // 删除Redis中缓存的库存
        for (StockReqeustDTO reqeustDTO : reqeustDTOList) {
            // 加分布式写锁
            RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock(RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.STOCK_DISTRIBUTED_LOCK, reqeustDTO.getSkuId()));
            RLock rLock = rReadWriteLock.writeLock();
            boolean lockRst = false;
            try {
                lockRst = rLock.tryLock(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                log.error("skuId:{}, aquire distributed write lock error:{}", reqeustDTO.getSkuId(), e.getMessage(), e);
            }
            if (!lockRst) {
                return new OccupyStockResponseDTO(-1, "skuId:" + reqeustDTO.getSkuId() + "获取分布式锁失败，请重试");
            }
            writeLockList.add(rLock);

            String redisKey = RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.STOCK_QTY, reqeustDTO.getSkuId());
            boolean deleteRst = redissonClient.getBucket(redisKey).delete();
            log.info("stock modify delete redis cache, key:{}, delete result:{}", redisKey, deleteRst);
        }

        // 操作数据库
        try {
            stockInventoryService.batchOccupyStock(reqeustDTOList);
        } catch (RuntimeException e) {
            errorMsg = e.getMessage();
        }

        // 释放分布式写锁
        writeLockList.forEach(Lock::unlock);

        if (errorMsg != null) {
            return new OccupyStockResponseDTO(-1, errorMsg);
        }
        return new OccupyStockResponseDTO(1, "订单占用库存成功");
    }


    /**
     * redis先删除，再更新数据库，此种场景的并发问题出现在，线程A刚删除key、还未更新数据库完成，线程B读取库中旧值写入redis
     * 所以锁的范围是 写锁：A删除redis --> 读锁：读数据库
     *
     * @param reqeustDTOList
     * @return
     */
    @Override
    public List<StockResponseDTO> getStock(List<StockReqeustDTO> reqeustDTOList) {

        List<StockResponseDTO> stockResponseDTOList = new ArrayList<>();

        // 预防死锁，按照skuId升序排序，依次加锁
        reqeustDTOList.sort((o1, o2) -> (int) (o1.getSkuId() - o2.getSkuId()));
        reqeustDTOList.forEach(reqeustDTO -> {
            String redisKey = RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.STOCK_QTY, reqeustDTO.getSkuId());
            RBucket<Integer> rBucket = redissonClient.getBucket(redisKey);
            Integer stockQty = rBucket.get();
            if (stockQty == null) {
                // 从库中抓取
                // 加分布式读锁
                RReadWriteLock rReadWriteLock = redissonClient.getReadWriteLock(RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.STOCK_DISTRIBUTED_LOCK, reqeustDTO.getSkuId()));
                RLock rLock = rReadWriteLock.readLock();
                rLock.lock();

                StockInventory stockInventory = stockInventoryService.findBySkuId(reqeustDTO.getSkuId());
                Optional.ofNullable(stockInventory).ifPresent(si -> {
                    rBucket.set(si.getStockNum().subtract(si.getOrderBookNum()).intValue());
                    Instant instant = Instant.now().plusMillis(TimeUnit.SECONDS.toMillis(RedisPrefixEnum.STOCK_QTY.getExpireSeconds()));
                    rBucket.expire(instant);

                    StockResponseDTO responseDTO = new StockResponseDTO(si.getSkuId(), null, si.getStockNum().intValue());
                    stockResponseDTOList.add(responseDTO);
                });
                rLock.unlock();
            } else {
                StockResponseDTO responseDTO = new StockResponseDTO(reqeustDTO.getSkuId(), null, stockQty);
                stockResponseDTOList.add(responseDTO);
            }
        });

        return stockResponseDTOList;
    }
}
