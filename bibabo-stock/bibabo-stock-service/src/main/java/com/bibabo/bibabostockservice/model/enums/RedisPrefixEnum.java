package com.bibabo.bibabostockservice.model.enums;

/**
 * @author fukuixiang
 * @date 2022/6/7
 * @time 15:57
 * @description
 */
public enum RedisPrefixEnum {

    STOCK_QTY("STOCK_QTY", "库存缓存", 60 * 60 * 24 * 30),
    STOCK_DISTRIBUTED_LOCK("STOCK_DISTRIBUTED_LOCK", "库存分布式读写锁", 0),
    ;

    private String prefix;
    private String desc;
    private int expireSeconds;

    RedisPrefixEnum(String prefix, String desc, int expireSeconds) {
        this.prefix = prefix;
        this.desc = desc;
        this.expireSeconds = expireSeconds;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getDesc() {
        return desc;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }


    public static String splicingRedisKey(RedisPrefixEnum redisPrefixEnum, Object... args) {
        if (null == redisPrefixEnum) {
            return null;
        }
        StringBuilder redisKeySB = new StringBuilder(redisPrefixEnum.getPrefix());
        if (null != args) {
            final String s = "_";
            for (Object obj : args) {
                if (null != obj) {
                    redisKeySB.append(s).append(obj.toString());
                }
            }
        }
        return redisKeySB.toString();
    }
}
