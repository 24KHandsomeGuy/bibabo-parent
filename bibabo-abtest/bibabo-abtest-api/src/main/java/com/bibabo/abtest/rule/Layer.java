package com.bibabo.abtest.rule;

import com.bibabo.abtest.model.AbExperiment;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 16:52
 * @Description 实验层次定义
 */
@Value
public class Layer {

    /**
     * 层标识
     */
    @NonNull
    String id;

    /**
     * [非必须] 扩展数据
     */
    String data;

    /**
     * 实验配置
     */
    List<AbExperiment> experiments;
}
