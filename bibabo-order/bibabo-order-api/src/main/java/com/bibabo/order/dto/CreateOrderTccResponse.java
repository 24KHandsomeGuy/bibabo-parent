package com.bibabo.order.dto;

import io.seata.rm.tcc.TwoPhaseResult;

/**
 * @Author: Damon Fu
 * @Date: 2022/5/31 22:03
 * @Description: TCC模式响应支持两种。1.boolean  2.派生自TwoPhaseResult
 */
public class CreateOrderTccResponse extends TwoPhaseResult {
    public CreateOrderTccResponse(boolean isSuccess, String msg) {
        super(isSuccess, msg);
    }
}
