package com.bibabo.bibaboorderservice.model.enums;

/**
 * @author fukuixiang
 * @date 2022/6/7
 * @time 15:57
 * @description
 */
public enum RedisPrefixEnum {

    ORDER_ADDRESS("ORDER_ADDRESS", "订单地址redisKey前缀", 60 * 60 * 24),
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
