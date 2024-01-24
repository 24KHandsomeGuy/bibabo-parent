package com.bibabo.abtest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/24 14:28
 * @Description AB测试分流命中的桶
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbBucket {

    /**
     * 桶顺序
     */
    private Integer sort;
    /**
     * 桶区间最小值
     */
    private Integer rangeMin;
    /**
     * 桶区间最大值
     */
    private Integer rangeMax;
}
