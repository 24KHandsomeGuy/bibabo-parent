package com.bibabo.abtest.config;

import com.alibaba.fastjson.JSONArray;
import com.bibabo.abtest.rule.AbRuleExecutorFactory;
import com.bibabo.abtest.rule.AbRuleConfig;
import com.bibabo.abtest.model.AbExperimentConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 15:00
 * @Description ab测试配置信息
 */
@Configuration
@Slf4j
public class ABTestConfiguration implements InitializingBean {

    /**
     * ab规则配置map集合
     */
    public static final Map<String, AbRuleConfig> abRuleConfigMap = new HashMap<>();

    /**
     * ab实验配置map集合
     */
    public static final Map<String, AbExperimentConfig> abExperimentConfigMap = new HashMap<>();

    @Value("${ab.rule.configs}")
    private String abRuleConfigs;

    @Value("${ab.experiment.configs}")
    private String abExperimentConfigs;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("ab rule configs: {}", abRuleConfigs);
        if (!StringUtils.isEmpty(abRuleConfigs)) {
            try {
                List<AbRuleConfig> ruleConfigList = AbRuleExecutorFactory.parseAbRuleConfig(abRuleConfigs);
                for (AbRuleConfig abRuleConfig : ruleConfigList) {
                    abRuleConfigMap.put(abRuleConfig.getDomain().getId().toString(), abRuleConfig);
                }
            } catch (Exception e) {
                log.error("ab rule configs parse error: {}", e.getMessage(), e);
            }
        } else {
            log.warn("ab.rule.configs is empty");
        }

        log.info("ab experiment configs: {}", abExperimentConfigs);
        if (!StringUtils.isEmpty(abExperimentConfigs)) {
            try {
                List<AbExperimentConfig> abExperimentConfigList = JSONArray.parseArray(abExperimentConfigs, AbExperimentConfig.class);
                for (AbExperimentConfig abExperimentConfig : abExperimentConfigList) {
                    abExperimentConfigMap.put(abExperimentConfig.getExperimentId().toString(), abExperimentConfig);
                }
            } catch (Exception e) {
                log.error("ab experiment configs parse error: {}", e.getMessage(), e);
            }
        } else {
            log.warn("ab.experiment.configs is empty");
        }
    }

}
