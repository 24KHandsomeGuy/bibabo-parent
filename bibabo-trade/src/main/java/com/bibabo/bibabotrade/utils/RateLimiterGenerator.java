package com.bibabo.bibabotrade.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/24 15:05
 * @Description:
 */
@Slf4j
@Service
public class RateLimiterGenerator {

    @Autowired
    RedissonClient redissonClient;

    @PostConstruct
    public void generateRateLimiter() {
        for (RedisRateLimiterEnum value : RedisRateLimiterEnum.values()) {
            RRateLimiter rRateLimiter = redissonClient.getRateLimiter(value.getResource());
            boolean trySetResult = rRateLimiter.trySetRate(value.getRateType(),
                    value.getRate(),
                    value.getRateInterval(),
                    value.getRateIntervalUnit());
            log.info("reasource redis key:{}, try set rate result:{}", value.getResource(), trySetResult);
        }

    }
}
