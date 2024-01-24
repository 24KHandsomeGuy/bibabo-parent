package com.bibabo.abtest.rule;

import com.bibabo.abtest.div.IRuleDivMethod;
import com.bibabo.abtest.div.RuleDivResult;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 16:41
 * @Description
 */
public interface AbTestRule {

    /**
     * 规则参数通过反射自动获取
     */
    AbTestRule autoHitRule(Map<String, Object> ruleParam);

    /**
     * 按灰度分层
     */
    AbTestRule hitRule(GrayPoint grayPoint);

    /**
     * 分流
     */
    RuleDivResult hitDiv(IRuleDivMethod divMethod);

    /**
     * 供系统调试用
     */
    default AbTestRule peek(Consumer<AbTestRule> action) {
        action.accept(this);
        return this;
    }

    AbTestRule NULL = new AbTestRule() {

        @Override
        public AbTestRule autoHitRule(Map<String, Object> ruleParam) {
            return NULL;
        }

        @Override
        public AbTestRule hitRule(GrayPoint grayPoint) {
            return NULL;
        }

        @Override
        public RuleDivResult hitDiv(IRuleDivMethod divMethod) {
            return new RuleDivResult(false);
        }

        @Override
        public String toString() {
            return "NullableAbTestPolicy{}";
        }
    };
}
