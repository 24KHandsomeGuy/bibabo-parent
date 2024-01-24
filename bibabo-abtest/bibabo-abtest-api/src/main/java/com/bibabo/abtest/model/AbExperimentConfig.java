package com.bibabo.abtest.model;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 14:27
 * @Description 试验业务对象
 */
@Data
public class AbExperimentConfig implements Serializable {

    /**
     * 试验Id
     */
    private Integer experimentId;
    /**
     * 试验名称
     */
    private String experimentName;
    /**
     * 试验 层级id
     */
    private Integer layerId;
    /**
     * 实验所在桶
     */
    private AbBucket bucket;
    /**
     * 每个试验的流量
     */
    private Integer flow;
    /**
     * 版本配置信息
     */
    private List<AbVersionConfig> versionConfigs;

    public AbExperimentConfig() {
    }

    public AbExperimentConfig(Integer experimentId, String experimentName, Integer layerId, List<AbVersionConfig> versionConfigs, Integer flow) {
        this.experimentId = experimentId;
        this.experimentName = experimentName;
        this.layerId = layerId;
        this.versionConfigs = versionConfigs;
        this.flow = flow;
    }

    /**
     * 根据版本流量获取版本号
     *
     * @param flow 计算的随机流量数
     * @return AB版本配置信息
     */
    public AbVersionConfig getVersionConfigByFlow(Integer flow) {
        if (CollectionUtil.isEmpty(versionConfigs)) return null;
        for (AbVersionConfig versionConfig : versionConfigs) {
            AbBucket bucket = versionConfig.getBucket();
            if (flow >= bucket.getRangeMin() && flow <= bucket.getRangeMax()) {
                return versionConfig;
            }
        }
        return null;
    }

}
