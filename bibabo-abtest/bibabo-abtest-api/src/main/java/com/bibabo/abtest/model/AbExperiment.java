package com.bibabo.abtest.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 15:31
 * @Description 试验对象
 */
@Data
public class AbExperiment implements Serializable {

    /**
     * 试验的id
     */
    private Integer experimentId;

    /**
     * 试验名称
     */
    private String experimentName;

    /**
     * 版本
     */
    private AbVersion version;

    /**
     * 层级id
     */
    private Integer layerId;

    /**
     * AB实验版本数据
     */
    private Object data;

    public AbExperiment() {
    }

    public AbExperiment(Integer experimentId, String experimentName, AbVersion version, Integer layerId) {
        this.experimentId = experimentId;
        this.experimentName = experimentName;
        this.version = version;
        this.layerId = layerId;
    }

    public AbExperiment(AbExperimentConfig experimentConfig, AbVersionConfig versionConfig) {
        this(experimentConfig.getExperimentId(), experimentConfig.getExperimentName(), AbVersion.byType(versionConfig.getVersion()), experimentConfig.getLayerId());
        this.data = versionConfig.getData();
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "experimentId=" + experimentId +
                ", experimentName=" + experimentName +
                ", version=" + version +
                ", layerId=" + layerId +
                '}';
    }
}
