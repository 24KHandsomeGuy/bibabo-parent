package com.bibabo.order.services;

import com.bibabo.order.dto.OrderModel;
import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 19:20
 * @Description:
 */
public interface OrderServiceI {

    OrderResponseDTO createOrder(OrderRequestDTO dto);

    OrderModel queryOrderById(long orderId);
}
