package com.bibabo.abtest.rule;

import lombok.Data;

import java.util.List;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 14:15
 * @Description AB实验配置类, eg:
 * <pre>
 *       [{"layer":{"id":"layer1","data":"something1"},"grayRules":[{"name":"source","enabled":true,"include":["2",
 *       "21"],"exclude":[],"global":false},{"name":"city","enabled":true,"include":["1","5"],"exclude":[],
 *       "global":false}],"divRule":{"percent":100}},{"layer":{"id":"layer2","data":"something2"},
 *       "grayRules":[{"name":"source","enabled":true,"include":["1"],"exclude":[],"global":false},{"name":"city",
 *       "enabled":true,"include":["3"],"exclude":[],"global":false}],"divRule":{"percent":100}}]
 * </pre>
 */
@Data
public class AbRuleConfig {

    /**
     * 实验域标识
     */
    private AbDomain domain;

    /**
     * 分层规则
     */
    private List<GrayRule> grayRules;

    /**
     * 分流规则
     */
    private DivRule divRule;
}
