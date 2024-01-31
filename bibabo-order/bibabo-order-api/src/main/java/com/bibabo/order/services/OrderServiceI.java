package com.bibabo.order.services;

import com.bibabo.order.dto.OrderAddressInfoDTO;
import com.bibabo.order.dto.OrderModel;
import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;
import com.bibabo.utils.model.ResponseDTO;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 19:20
 * @Description:
 */
public interface OrderServiceI {

    OrderResponseDTO createOrder(OrderRequestDTO dto);

    OrderModel queryOrderById(long orderId);

    OrderResponseDTO payOrder(long orderId);

    /**
     * 查询订单地址，用于Redis缓存试验
     *
     * @param orderId
     * @return
     */
    ResponseDTO<OrderAddressInfoDTO> queryOrderAddress(long orderId);

    /**
     * 修改订单地址，用于Redis缓存试验
     *
     * @param dto
     * @return
     */
    ResponseDTO updateOrderAddress(OrderAddressInfoDTO dto);
}
