package com.bibabo.abtest.rule;

import lombok.NonNull;
import lombok.Value;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 16:43
 * @Description
 */
@Value
public class BasicGrayPoint implements GrayPoint {

    @NonNull
    String name;

    @NonNull
    String value;

}
