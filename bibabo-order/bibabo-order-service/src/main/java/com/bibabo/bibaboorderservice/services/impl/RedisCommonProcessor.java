package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.bibaboorderservice.model.enums.RedisPrefixEnum;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author fukuixiang
 * @date 2022/6/8
 * @time 17:22
 * @description
 */
@Component
public class RedisCommonProcessor {

    @Autowired
    private RedissonClient redissonClient;


    public boolean deleteRedisKey(String key) {
        return redissonClient.getBucket(key).delete();
    }
}
