package com.bibabo.order.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 19:19
 * @Description:
 */
@Data
public class OrderRequestDTO extends BaseDTO {

    private OrderModel orderModel;
}
