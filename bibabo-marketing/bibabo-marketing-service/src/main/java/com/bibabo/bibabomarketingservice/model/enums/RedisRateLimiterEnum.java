package com.bibabo.bibabomarketingservice.model.enums;

import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/24 15:07
 * @Description:
 */
public enum RedisRateLimiterEnum {
    QUEUE_ACTIVITY("QUEUE_ACTIVITY", RateType.OVERALL, 120, 1, RateIntervalUnit.MINUTES),
    QUEUE_GRANT_COUPONS("QUEUE_GRANT_COUPONS", RateType.OVERALL, 30, 1, RateIntervalUnit.MINUTES);

    RedisRateLimiterEnum(String resource, RateType rateType, long rate, long rateInterval, RateIntervalUnit rateIntervalUnit) {
        this.resource = resource;
        this.rateType = rateType;
        this.rate = rate;
        this.rateInterval = rateInterval;
        this.rateIntervalUnit = rateIntervalUnit;
    }

    // 被限制的资源
    private String resource;

    // 速率的类型。分布式总体或者每个客户端
    private RateType rateType;

    // 具体速率大小
    private long rate;

    // 速率的时间间隔
    private long rateInterval;

    // 速率的时间单位
    private RateIntervalUnit rateIntervalUnit;

    public String getResource() {
        return resource;
    }

    public RateType getRateType() {
        return rateType;
    }

    public long getRate() {
        return rate;
    }

    public long getRateInterval() {
        return rateInterval;
    }

    public RateIntervalUnit getRateIntervalUnit() {
        return rateIntervalUnit;
    }
}
