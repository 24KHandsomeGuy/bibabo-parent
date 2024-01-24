package com.bibabo.abtest.execute;

import cn.hutool.core.collection.CollectionUtil;
import com.bibabo.abtest.DefaultExperimentHandler;
import com.bibabo.abtest.model.AbBucket;
import com.bibabo.abtest.model.AbExperimentConfig;
import com.bibabo.abtest.model.AbVersionConfig;
import lombok.Data;

import java.util.List;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 15:27
 * @Description 默认分流处理的构造者
 */
@Data
public class AbTestConfig {

    private AbTestConfig() {
    }

    private AbstractExperimentHandler experimentHandler;

    public static AbTestConfig me() {
        return new AbTestConfig();
    }

    /**
     * 构造试验流处理责任链对象，试验集合中试验id唯一
     *
     * @param experimentConfigList 试验对象集合，装配按照list顺序进行从后向前装配（即第一个队形最后处理）。需要按照开始时间降序传入（最后开始的放在最后一个处理）。
     * @return 默认
     */
    public AbTestConfig builder(List<AbExperimentConfig> experimentConfigList) {
        if (CollectionUtil.isEmpty(experimentConfigList)) {
            return null;
        }
        DefaultExperimentHandler result = null;
        for (AbExperimentConfig experimentConfig : experimentConfigList) {
            fillAbVersionBucket(experimentConfig.getVersionConfigs());
            DefaultExperimentHandler experSplitHandler = new DefaultExperimentHandler(experimentConfig);
            experSplitHandler.setNextHandler(result);
            result = experSplitHandler;
        }
        fillAbBucket(null, result, 1);
        this.experimentHandler = result;
        return this;
    }

    /**
     * 填充实验各版本所属的流量桶
     *
     * @param versionConfigs 版本信息配置
     */
    private void fillAbVersionBucket(List<AbVersionConfig> versionConfigs) {
        for (int i = 0; i < versionConfigs.size(); i++) {
            AbVersionConfig versionConfig = versionConfigs.get(i);
            Integer rangeMin = 0;
            if (i > 0) {
                rangeMin += versionConfigs.get(i - 1).getBucket().getRangeMax();
            }
            AbBucket abBucket = new AbBucket(i + 1, rangeMin + 1, rangeMin + versionConfig.getFlow());
            versionConfig.setBucket(abBucket);
        }
    }

    /**
     * 填充一层中多个实验各自所在的桶
     *
     * @param prev                     前一个实验
     * @param defaultExperSplitHandler 当前实验
     * @param seq                      实验排序号
     */
    private void fillAbBucket(DefaultExperimentHandler prev, DefaultExperimentHandler defaultExperSplitHandler, Integer seq) {
        AbExperimentConfig abExperimentConfig = defaultExperSplitHandler.getAbExperimentConfig();
        Integer rangeMin = 0;
        if (prev != null) {
            rangeMin += prev.getAbExperimentConfig().getBucket().getRangeMax();
        }
        AbBucket abBucket = new AbBucket(seq, rangeMin + 1, rangeMin + abExperimentConfig.getFlow());
        abExperimentConfig.setBucket(abBucket);
        DefaultExperimentHandler next = (DefaultExperimentHandler) defaultExperSplitHandler.getNextHandler();
        if (next != null) {
            fillAbBucket(defaultExperSplitHandler, next, ++seq);
        }
    }
}
