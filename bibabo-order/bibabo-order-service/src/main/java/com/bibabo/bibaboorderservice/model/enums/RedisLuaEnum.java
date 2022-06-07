package com.bibabo.bibaboorderservice.model.enums;

/**
 * @author fukuixiang
 * @date 2022/6/7
 * @time 15:50
 * @description
 */
public enum RedisLuaEnum {

    SET_AND_EXPIRE_TIME(1, "设置值，成功设置超时时间", "local rst = redis.call('SET', KEYS[1], ARGV[1]);redis.call('EXPIRE', KEYS[1], ARGV[2]); return rst;"),
    SETNX_AND_EXPIRE_TIME(1, "key不存在设置值，成功设置超时时间", "local rst = redis.call('SETNX', KEYS[1], ARGV[1]);if(rst == 1) then redis.call('EXPIRE', KEYS[1], ARGV[2]) end;return rst;"),
    ;

    private Integer id;
    private String desc;
    private String luaScript;

    RedisLuaEnum(Integer id, String desc, String luaScript) {
        this.id = id;
        this.desc = desc;
        this.luaScript = luaScript;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getLuaScript() {
        return luaScript;
    }

}
