package com.bibabo.bibabotrade.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 17:13
 * @description
 */
@Data
@AllArgsConstructor
public class CreateOrderVO implements Serializable {

    private Boolean success;

    private Long orderId;

    private String errorMsg;
}
