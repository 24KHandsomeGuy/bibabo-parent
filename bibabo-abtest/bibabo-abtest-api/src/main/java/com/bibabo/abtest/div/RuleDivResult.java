package com.bibabo.abtest.div;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 16:41
 * @Description 分层、分流后的最终返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleDivResult {

    /**
     * 是否命中分层、分流规则
     */
    protected boolean hit;
    /**
     * 层次定义
     */
    //protected Layer layer;

}
