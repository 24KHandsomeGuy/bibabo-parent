/*
package com.bibabo.bibabostockservice.services.impl;

import com.bibabo.stock.dto.OccupyStockResponseDTO;
import com.bibabo.stock.dto.StockRedisDTO;
import com.bibabo.stock.dto.StockReqeustDTO;
import com.bibabo.stock.dto.StockResponseDTO;
import com.bibabo.stock.services.StockServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

*/
/**
 * @author fukuixiang
 * @date 2022/4/27
 * @time 15:35
 * @description
 *//*

@Slf4j
@DubboService
public class StockServiceRedisImpl implements StockServiceI {

    private static final String prefix = "stock_";

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    private void init() {
        long startSkuId = 100;
        for (int i = 0; i < 100; i++) {
            long skuId = startSkuId + i;
            RBucket<StockRedisDTO> rBucket = redissonClient.getBucket(prefix + skuId);
            rBucket.set(new StockRedisDTO(skuId, 1, 10));
            Instant instant = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(1));
            rBucket.expire(instant);
        }
    }

    @Override
    public OccupyStockResponseDTO occupyStock(List<StockReqeustDTO> reqeustDTOList) {
        Instant instant = Instant.now().plusMillis(TimeUnit.HOURS.toMillis(1));
        Map<Long, Integer> shortSkuIdsMap = new HashMap<>();
        reqeustDTOList.forEach(dto -> {
            RBucket<StockRedisDTO> rBucket = redissonClient.getBucket(prefix + dto.getSkuId());
            StockRedisDTO redisDTO = rBucket.get();
            int surplusStock = redisDTO.getStockNum() - dto.getSkuNum();
            if (surplusStock < 0) {
                shortSkuIdsMap.put(dto.getSkuId(), surplusStock);
                return;
            }
            redisDTO.setStockNum(surplusStock);
            rBucket.set(redisDTO);
            rBucket.expire(instant);
        });
        if (shortSkuIdsMap.size() <= 0) {
            return new OccupyStockResponseDTO(1, "占用成功");
        }
        StringBuilder sb = new StringBuilder();
        shortSkuIdsMap.forEach((skuId, shortNum) -> {
            sb.append("sku:").append(skuId).append("，报缺数量:").append(shortNum).append("; ");
        });

        return new OccupyStockResponseDTO(-1, sb.toString());
    }

    @Override
    public List<StockResponseDTO> getStock(List<StockReqeustDTO> reqeustDTOList) {
        List<StockResponseDTO> responseDTOList = new ArrayList<>();
        reqeustDTOList.parallelStream().forEach(dto -> {
            RBucket<StockRedisDTO> redisDTORBucket = redissonClient.getBucket(prefix + dto.getSkuId());
            Integer stockNum = redisDTORBucket.get().getStockNum();
            StockResponseDTO responseDTO = new StockResponseDTO(dto.getSkuId(), dto.getWareId(), stockNum);
            responseDTOList.add(responseDTO);
        });
        return responseDTOList;
    }
}
*/
