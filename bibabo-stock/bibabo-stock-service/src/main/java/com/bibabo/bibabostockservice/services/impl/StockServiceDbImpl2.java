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
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 15:35
 * @description 性能优化:
 * 1.如分段锁思想：可以把每个库存Key分为10段存储。sku10086分为10086-01、10086-02、10086-03...，每个段拥有一把锁。
 * 内写一个负载均衡算法，如轮询依次把锁分到这10个段上
 * 一个段位减完后，尝试扣减其他段位
 * 理想情况下，性能提升10倍
 * 2.读多写少，分布式读写锁
 *//*
@Slf4j
@DubboService
public class StockServiceDbImpl2 implements StockServiceI {

    @Autowired
    private StockInventoryService stockInventoryService;

    @Autowired
    RedissonClient redissonClient;


    @Override
    public OccupyStockResponseDTO occupyStock(List<StockReqeustDTO> reqeustDTOList) {
        String errorMsg = null;

        // 预防死锁，按照skuId升序排序，依次加锁
        // TODO 多锁合并
        RLock[] rLocks =
                (RLock[]) reqeustDTOList.stream()
                        .sorted((o1, o2) -> (int) (o1.getSkuId() - o2.getSkuId()))
                        .map(stockReqeustDTO -> redissonClient.getReadWriteLock(RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.STOCK_DISTRIBUTED_LOCK, stockReqeustDTO.getSkuId())).writeLock())
                        .toArray();
        RLock multiWriteLock = redissonClient.getMultiLock(rLocks);
        boolean lockRst = false;
        try {
            lockRst = multiWriteLock.tryLock(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("aquire distributed write lock error:{}", e.getMessage(), e);
        }
        if (!lockRst) {
            return new OccupyStockResponseDTO(-1, "获取分布式锁失败，请重试");
        }
        // 删除Redis中缓存的库存
        for (StockReqeustDTO reqeustDTO : reqeustDTOList) {
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
        multiWriteLock.unlock();

        if (errorMsg != null) {
            return new OccupyStockResponseDTO(-1, errorMsg);
        }
        return new OccupyStockResponseDTO(1, "订单占用库存成功");
    }


    *//**
     * redis先删除，再更新数据库，此种场景的并发问题出现在，线程A刚删除key、还未更新数据库完成，线程B读取库中旧值写入redis
     * 所以锁的范围是 写锁：A删除redis --> 读锁：读数据库
     *
     * @param reqeustDTOList
     * @return
     *//*
    @Override
    public List<StockResponseDTO> getStock(List<StockReqeustDTO> reqeustDTOList) {

        List<StockResponseDTO> stockResponseDTOList = new ArrayList<>();

        // 预防死锁，按照skuId升序排序，依次加锁
        RLock[] rLocks = (RLock[]) reqeustDTOList.stream()
                .sorted((o1, o2) -> (int) (o1.getSkuId() - o2.getSkuId()))
                .map(stockReqeustDTO -> redissonClient.getReadWriteLock(RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.STOCK_DISTRIBUTED_LOCK, stockReqeustDTO.getSkuId())))
                .toArray();
        RLock multiReadLock = redissonClient.getMultiLock(rLocks);
        boolean lockRst = false;
        try {
            lockRst = multiReadLock.tryLock(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            log.error("aquire distributed write lock error:{}", e.getMessage(), e);
        }
        if (!lockRst) {
            return null;
        }

        reqeustDTOList.forEach(reqeustDTO -> {
            String redisKey = RedisPrefixEnum.splicingRedisKey(RedisPrefixEnum.STOCK_QTY, reqeustDTO.getSkuId());
            RBucket<Integer> rBucket = redissonClient.getBucket(redisKey);
            Integer stockQty = rBucket.get();
            if (stockQty == null) {
                StockInventory stockInventory = stockInventoryService.findBySkuId(reqeustDTO.getSkuId());
                Optional.ofNullable(stockInventory).ifPresent(si -> {
                    rBucket.set(si.getStockNum().subtract(si.getOrderBookNum()).intValue());
                    Instant instant = Instant.now().plusMillis(TimeUnit.SECONDS.toMillis(RedisPrefixEnum.STOCK_QTY.getExpireSeconds()));
                    rBucket.expire(instant);

                    StockResponseDTO responseDTO = new StockResponseDTO(si.getSkuId(), null, si.getStockNum().intValue());
                    stockResponseDTOList.add(responseDTO);
                });
            } else {
                StockResponseDTO responseDTO = new StockResponseDTO(reqeustDTO.getSkuId(), null, stockQty);
                stockResponseDTOList.add(responseDTO);
            }
        });

        multiReadLock.unlock();
        return stockResponseDTOList;
    }
}
*/