package com.bibabo.abtest.trace;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 16:48
 * @Description
 */
public class BaseJsonObj implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return this.toJsonStr();
    }

    public String toJsonStr() {
        return JSON.toJSONString(this);
    }
}
