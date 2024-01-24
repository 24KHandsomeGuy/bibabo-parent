package com.bibabo.abtest.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 14:31
 * @Description AB版本配置信息
 */
@Data
public class AbVersionConfig implements Serializable {

    /**
     * 版本号对应的数值
     *
     * @see com.bibabo.abtest.model.AbVersion
     */
    private Integer version;
    /**
     * 每个试验版本的流量
     */
    private Integer flow;
    /**
     * 分流所在桶
     */
    private AbBucket bucket;
    /**
     * 版本业务数据
     */
    private Object data;

    public AbVersionConfig() {
    }

    public AbVersionConfig(Integer version, Integer flow, Object data) {
        this.version = version;
        this.flow = flow;
        this.data = data;
    }
}
