package com.bibabo.bibabotrade.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fukuixiang
 * @date 2022/4/12
 * @time 15:43
 * @description
 */
@Service
@Slf4j
public class NumberGenerator {

    private static final String ORDER_NUMBER_GENERATE_KEY = "/NUMBER_GENERATE/ORDER_ID";

    @Autowired
    RedissonClient redissonClient;

    public long generateOrderId() {
        // raddlong 服务关闭就删掉了
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong(ORDER_NUMBER_GENERATE_KEY);
        long orderId = rAtomicLong.incrementAndGet();
        log.info(String.format("订单号生成器generateOrderId，orderId: %d", orderId));
        return orderId;
    }
}
