package com.bibabo.order.dto;

import lombok.Data;

/**
 * @author fukuixiang
 * @date 2022/6/7
 * @time 13:55
 * @description
 */
@Data
public class OrderAddressInfoDTO extends BaseDTO {

    private Long orderId;

    private String custAddress;
}
