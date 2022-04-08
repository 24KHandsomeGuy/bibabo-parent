package com.bibabo.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 19:21
 * @Description:
 */
@Data
@AllArgsConstructor
public class OrderResponseDTO extends BaseDTO {

    private Boolean success;

    private Long orderId;
}
