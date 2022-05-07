package com.bibabo.bibabotrade.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 17:13
 * @description
 */
@Data
@AllArgsConstructor
public class OrderPayVO {

    private Boolean success;

    private Long orderId;

    private String errorMsg;
}
