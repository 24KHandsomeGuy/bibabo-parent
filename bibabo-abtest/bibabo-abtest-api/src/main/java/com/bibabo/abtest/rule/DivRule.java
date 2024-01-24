package com.bibabo.abtest.rule;

import lombok.Value;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 14:29
 * @Description 分流规则
 */
@Value
public class DivRule {

    /**
     * 分流比例
     */
    int percent;

    public boolean hitRule(int indicator) {
        if (indicator < 0 || indicator >= 100) {
            throw new IllegalStateException("Indicator should be [0,100)");
        }
        return indicator < percent;
    }
}
