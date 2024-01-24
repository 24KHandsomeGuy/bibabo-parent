package com.bibabo.abtest.rule;

import cn.hutool.core.collection.CollectionUtil;
import com.bibabo.abtest.div.IRuleDivMethod;
import com.bibabo.abtest.div.RuleDivResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.NonNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 15:13
 * @Description AB实验工厂类
 */
public abstract class AbRuleExecutorFactory {

    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    public static List<AbRuleConfig> parseAbRuleConfig(@NonNull String abRuleConfigs) {
        Type type = new TypeToken<List<AbRuleConfig>>() {
        }.getType();
        return gson.fromJson(abRuleConfigs, type);
    }

    public static AbRuleExecutor build(@NonNull String abRuleConfigs) {
        return build(parseAbRuleConfig(abRuleConfigs));
    }

    public static AbRuleExecutor build(@NonNull List<AbRuleConfig> abRuleConfigs) {
        List<AbTestRule> policyList = abRuleConfigs.stream().map(config -> build(config.getDomain(), config.getGrayRules(), config.getDivRule())).collect(Collectors.toList());
        return new AbRuleExecutor(policyList);
    }

    private static AbTestRule build(AbDomain abDomain, List<GrayRule> grayRules, DivRule divRule) {
        return new BasicAbTestPolicy(abDomain, grayRules, divRule);
    }

    /**
     * 组合策略类，当做入口类对外暴露
     * 组合模式
     */
    public static class AbRuleExecutor implements AbTestRule {

        private final List<AbTestRule> policyList;

        private AbRuleExecutor(@NonNull List<AbTestRule> policyList) {
            this.policyList = policyList;
        }

        @Override
        public AbTestRule autoHitRule(Map<String, Object> ruleParam) {
            AbTestRule curPolicy = AbTestRule.NULL;
            // 遍历规则依次校验，只要有一个校验通过，则为通过
            for (AbTestRule policy : policyList) {
                curPolicy = policy.autoHitRule(ruleParam);
                if (curPolicy.getClass() == BasicAbTestPolicy.class) {
                    break;
                }
            }
            return curPolicy;
        }

        /**
         * 分层
         */
        public AbTestRule hitRule(GrayPoint grayPoint) {
            AbTestRule curPolicy = AbTestRule.NULL;
            for (AbTestRule policy : policyList) {
                curPolicy = policy.hitRule(grayPoint);
                if (curPolicy.getClass() == BasicAbTestPolicy.class) {
                    break;
                }
            }
            return curPolicy;
        }

        /**
         * 分流
         */
        public RuleDivResult hitDiv(IRuleDivMethod divMethod) {
            throw new UnsupportedOperationException("Please call 'hitLayer' first");
        }

    }

    /**
     * 校验灰度规则和分流的实际执行类
     */
    private static class BasicAbTestPolicy implements AbTestRule {

        /**
         * 域标识
         */
        private final AbDomain abDomain;

        /**
         * 灰度规则
         */
        private final List<GrayRule> grayRules;

        /**
         * 分流规则
         */
        private final DivRule divRule;

        private BasicAbTestPolicy(@NonNull AbDomain abDomain, @NonNull List<GrayRule> grayRules, @NonNull DivRule divRule) {
            this.abDomain = abDomain;
            this.grayRules = grayRules;
            this.divRule = divRule;
        }

        @Override
        public AbTestRule autoHitRule(Map<String, Object> ruleParam) {
            AbTestRule curPolicy = AbTestRule.NULL;
            List<GrayPoint> grayPointList = new ArrayList<>(grayRules.size());
            for (GrayRule grayRule : grayRules) {
                Object fieldValueObj = ruleParam.get(grayRule.getName());
                String fieldValue = fieldValueObj == null ? "" : fieldValueObj.toString();
                grayPointList.add(new BasicGrayPoint(grayRule.getName(), fieldValue));
            }
            if (CollectionUtil.isEmpty(grayPointList)) {
                return curPolicy;
            }
            // 遍历灰组规则依次校验，只要有一个校验不通过，则为不通过
            for (GrayPoint grayPoint : grayPointList) {
                curPolicy = this.hitRule(grayPoint);
                if (curPolicy.getClass() != BasicAbTestPolicy.class) {
                    break;
                }
            }
            return curPolicy;
        }

        @Override
        public AbTestRule hitRule(GrayPoint grayPoint) {
            Optional<GrayRule> optRule = grayRules.stream().filter(rule -> rule.isEnabled() && rule.getName().equals(grayPoint.getName())).findFirst();
            if (!optRule.isPresent()) {
                return AbTestRule.NULL;
            }
            boolean hitRule = optRule.get().hitRule(grayPoint.getValue());
            if (!hitRule) {
                return AbTestRule.NULL;
            }
            // 返回自身, 做到可链式调用
            return this;
        }

        @Override
        public RuleDivResult hitDiv(IRuleDivMethod divMethod) {
            boolean b = divRule.hitRule(divMethod.calcIndicator());
            return new RuleDivResult(b);
        }
    }
}
