package com.bibabo.abtest;

import com.bibabo.abtest.execute.AbstractExperimentHandler;
import com.bibabo.abtest.model.AbExperimentConfig;
import com.bibabo.abtest.model.AbExperimentMark;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 16:38
 * @Description 试验分流具体类
 */
public class DefaultExperimentHandler extends AbstractExperimentHandler {

    private AbExperimentConfig abExperimentConfig;

    public DefaultExperimentHandler() {
        super();
    }

    public DefaultExperimentHandler(AbExperimentConfig abExperimentConfig) {
        super();
        this.abExperimentConfig = abExperimentConfig;
    }

    public AbExperimentConfig getAbExperimentConfig() {
        return abExperimentConfig;
    }

    public void setAbExperimentConfig(AbExperimentConfig abExperimentConfig) {
        this.abExperimentConfig = abExperimentConfig;
    }

    @Override
    public boolean through(AbExperimentMark request, AbExperimentMark response, IDivMethod divMethod) {
        through(response);
        return divMethod.hitDiv(request, response, abExperimentConfig);
    }

    @Override
    protected void through(AbExperimentMark response) {
        if (abExperimentConfig == null) {
            //试验数据空，分流结束
            return;
        }
        //添加参与标记
        response.getParticipationSet().add(abExperimentConfig.getExperimentId());
    }

}
