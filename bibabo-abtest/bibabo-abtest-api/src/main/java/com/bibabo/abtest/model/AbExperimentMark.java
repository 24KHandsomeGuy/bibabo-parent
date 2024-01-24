package com.bibabo.abtest.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 15:30
 * @Description 试验标记对象
 */
public class AbExperimentMark implements Serializable {

    /**
     * 参与标记集合，代表参与了试验，最少一个
     */
    private Set<Integer> participationSet = new HashSet<>();
    /**
     * 分配的试验，可为空
     */
    private AbExperiment experiment;

    public Set<Integer> getParticipationSet() {
        return participationSet;
    }

    public void setParticipationSet(Set<Integer> participationSet) {
        this.participationSet = participationSet;
    }

    public AbExperiment getExperiment() {
        return experiment;
    }

    public void setExperiment(AbExperiment experiment) {
        this.experiment = experiment;
    }

    @Override
    public String toString() {
        return "ExperimentMark{" +
                "participationSet=" + participationSet +
                ", experiment=" + experiment +
                '}';
    }
}
