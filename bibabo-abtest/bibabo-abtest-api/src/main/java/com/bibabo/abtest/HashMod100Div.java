package com.bibabo.abtest;

import com.bibabo.abtest.model.AbBucket;
import com.bibabo.abtest.model.AbExperiment;
import com.bibabo.abtest.model.AbExperimentConfig;
import com.bibabo.abtest.model.AbExperimentMark;
import com.bibabo.abtest.model.AbVersionConfig;
import com.bibabo.abtest.util.ABTestUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 15:42
 * @Description hash取模100
 */
@Slf4j
public class HashMod100Div implements IDivMethod {
    private final Object input;

    public HashMod100Div(Object input) {
        this.input = input;
    }

    @Override
    public boolean hitDiv(AbExperimentMark request, AbExperimentMark response, AbExperimentConfig abExperimentConfig) {
        if (request != null && request.getParticipationSet().contains(abExperimentConfig.getExperimentId())) {
            //携带参与标记
            if (checkExperimentMark(request.getExperiment(), abExperimentConfig)) {
                //携带此试验的试验标记，放入试验，分流结束
                response.setExperiment(request.getExperiment());
                return true;
            }
        } else {
            //生成随机数并分流
            // hash(input+层id)按层流量打散
            String encodeMD5 = ABTestUtil.encodeMD5(input.toString() + abExperimentConfig.getLayerId());
            int indicator = (Math.abs(Objects.hashCode(encodeMD5)) % 100) + 1;
            AbBucket abBucket = abExperimentConfig.getBucket();
            if (indicator < abBucket.getRangeMin() || indicator > abBucket.getRangeMax()) return false;
            //注意：这里计算版本流量要重新打散所以需要加上实验ID纬度来打散流量
            String versionEncodeMD5 = ABTestUtil.encodeMD5(input.toString() + abExperimentConfig.getLayerId() + abExperimentConfig.getExperimentId());
            int versionIndicator = (Math.abs(Objects.hashCode(versionEncodeMD5)) % 100) + 1;
            AbVersionConfig versionConfig = abExperimentConfig.getVersionConfigByFlow(versionIndicator);
            if (versionConfig == null) return false;
            //int version = indicator % abExperimentConfig.getNum() + 1;
            AbExperiment split = new AbExperiment(abExperimentConfig, versionConfig);
            response.setExperiment(split);
            return true;
        }
        return false;
    }

    /**
     * 检查试验参与标记是否符合规范命中
     *
     * @param experiment         试验标记
     * @param abExperimentConfig 试验对象
     * @return 是否真的命中
     */
    private boolean checkExperimentMark(AbExperiment experiment, AbExperimentConfig abExperimentConfig) {
        return experiment != null && experiment.getExperimentId().equals(abExperimentConfig.getExperimentId()) && experiment.getLayerId().equals(abExperimentConfig.getLayerId()) && experiment.getVersion().getType() <= abExperimentConfig.getVersionConfigs().size();
    }
}
