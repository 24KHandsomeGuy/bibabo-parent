package com.bibabo.abtest.rule;

import lombok.NonNull;
import lombok.Value;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 14:16
 * @Description 域配置
 * @Value  不可变成员变量
 */
@Value
public class AbDomain {

    /**
     * 域标识
     */
    @NonNull
    Long id;

    String name;
}
