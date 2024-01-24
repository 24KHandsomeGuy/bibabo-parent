package com.bibabo.abtest.div;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 16:41
 * @Description 分流方式
 */
public interface IRuleDivMethod {

    /**
     * 计算分流指标: 合法值应落在[0,100)区间
     */
    int calcIndicator();

    IRuleDivMethod GLOBAL_DIV = () -> 0;

}
