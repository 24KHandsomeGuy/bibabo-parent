package com.bibabo.abtest.rule;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 16:42
 * @Description 灰度切入点
 */
public interface GrayPoint {

    /**
     * 灰度规则名称, eg: "uid"、"city"
     */
    String getName();

    /**
     * 灰度规则待校验是否匹配的值
     */
    String getValue();
}
