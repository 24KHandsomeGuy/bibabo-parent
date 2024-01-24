package com.bibabo.abtest.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.bibabo.abtest.rule.AbRuleExecutorFactory;
import com.bibabo.abtest.execute.AbTest;
import com.bibabo.abtest.execute.AbTestConfig;
import com.bibabo.abtest.HashMod100Div;
import com.bibabo.abtest.rule.AbRuleConfig;
import com.bibabo.abtest.config.ABTestConfiguration;
import com.bibabo.abtest.div.RuleDivMethods;
import com.bibabo.abtest.div.RuleDivResult;
import com.bibabo.abtest.model.AbExperiment;
import com.bibabo.abtest.model.AbExperimentConfig;
import com.bibabo.abtest.model.AbExperimentMark;
import com.bibabo.abtest.dto.AbTestRequest;
import com.bibabo.abtest.dto.AbTestResponse;
import com.bibabo.abtest.services.IAbTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 13:52
 * @Description TODO 没环境 先使用本地测试，后续使用dubbo测试
 */
@Service
@Slf4j
public class AbTestImpl implements IAbTest {

    private static final String SUFFIX_ = "-";

    @Override
    public AbTestResponse getAbTest(AbTestRequest abTestRequest) {
        log.info("AB test service accept request:{}", JSONObject.toJSONString(abTestRequest));
        if (abTestRequest.getDomainId() == null || abTestRequest.getExperimentId() == null) {
            log.warn("AB test service accept request domain id or experiment id is null:{}", JSONObject.toJSONString(abTestRequest));
            return AbTestResponse.builder().build();
        }
        if (abTestRequest.getUserId() == null) {
            log.warn("AB test service accept request user id is null:{}", JSONObject.toJSONString(abTestRequest));
            return AbTestResponse.builder().build();
        }
        try {
            // 1.校验是否命中规则
            String domainId = abTestRequest.getDomainId().toString();
            AbRuleConfig abRuleConfig = ABTestConfiguration.abRuleConfigMap.get(domainId);
            if (abRuleConfig == null) {
                log.warn("AB test service accept request ab rule config is null:{}", JSONObject.toJSONString(abTestRequest));
                return AbTestResponse.builder().build();
            }
            AbRuleExecutorFactory.AbRuleExecutor abRuleExecutor = AbRuleExecutorFactory.build(Collections.singletonList(abRuleConfig));
            RuleDivResult ruleDivResult = abRuleExecutor.autoHitRule(abTestRequest).hitDiv(RuleDivMethods.hashMod(abTestRequest.getUserId()));
            log.info("AB test rule div result: {}, abTestRequest: {}", ruleDivResult.isHit(), JSONObject.toJSONString(abTestRequest));
            if (!ruleDivResult.isHit()) {
                return AbTestResponse.builder().build();
            }

            // 2.实验列表获取A/B结果
            AbExperimentConfig abExperimentConfig = ABTestConfiguration.abExperimentConfigMap.get(abTestRequest.getExperimentId().toString());
            if (abExperimentConfig == null) {
                log.warn("AB test service accept request ab experiment config is null:{}", JSONObject.toJSONString(abTestRequest));
                return AbTestResponse.builder().build();
            }
            AbTestConfig config = AbTestConfig.me().builder(Collections.singletonList(abExperimentConfig));
            AbExperimentMark request = new AbExperimentMark();
            AbExperimentMark response = AbTest
                    .me()
                    .config(config)
                    .request(request)
                    .divMethod(new HashMod100Div(abTestRequest.getUserId()))
                    .execute();
            AbExperiment abExperiment = response.getExperiment();
            if (abExperiment != null) {
                String abFlag = abExperiment.getLayerId() + SUFFIX_ + abExperiment.getExperimentId() + SUFFIX_ + abExperiment.getVersion().getVersion();
                //添加A/B测试日志落表
                //abTestLogService.saveAsync(abTestParam, abExperiment);
                return AbTestResponse.builder().abFlag(abFlag).abVersion(abExperiment.getVersion()).data(abExperiment.getData()).build();
            }
        } catch (Exception e) {
            log.error("AB test service error:{}", e.getMessage(), e);
            return AbTestResponse.builder().build();
        }
        return AbTestResponse.builder().build();
    }
}
