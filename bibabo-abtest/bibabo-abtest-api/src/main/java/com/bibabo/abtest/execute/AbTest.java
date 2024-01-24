package com.bibabo.abtest.execute;

import com.bibabo.abtest.IDivMethod;
import com.bibabo.abtest.model.AbExperimentMark;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 16:42
 * @Description
 */
public class AbTest {

    private AbTestConfig abTestConfig;

    private AbExperimentMark request;

    private IDivMethod divMethod;

    private AbTest() {
    }

    public static AbTest me() {
        return new AbTest();
    }

    public AbTest config(AbTestConfig abTestConfig) {
        this.abTestConfig = abTestConfig;
        return this;
    }

    public AbTest request(AbExperimentMark request) {
        this.request = request;
        return this;
    }

    public AbTest divMethod(IDivMethod divMethod) {
        this.divMethod = divMethod;
        return this;
    }

    public AbExperimentMark execute() {
        if (this.abTestConfig == null) {
            throw new UnsupportedOperationException("Please call 'config' first");
        }
        if (this.divMethod == null) {
            this.divMethod = new DefaultDiv();
        }
        AbExperimentMark response = new AbExperimentMark();
        this.abTestConfig.getExperimentHandler().handler(this.request, response, divMethod);
        return response;
    }
}
