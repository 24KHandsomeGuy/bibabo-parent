package com.bibabo.abtest;

import com.bibabo.abtest.model.AbExperimentConfig;
import com.bibabo.abtest.model.AbExperimentMark;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 15:35
 * @Description 分流对象接口, 继承实现自定义接口
 */
public interface IDivMethod {

    /**
     * 进行分流
     *
     * @param request  携带的标记
     * @param response 响应的标记
     * @param experBo  试验的业务信息
     * @return 分流结束标记
     */
    boolean hitDiv(AbExperimentMark request, AbExperimentMark response, AbExperimentConfig experBo);
}
