package com.bibabo.bibabotrade.services;

import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.vo.CreateOrderVO;
import com.bibabo.order.dto.OrderModel;
import com.bibabo.order.dto.OrderResponseDTO;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 17:10
 * @description
 */
public interface CreateOrderServiceI {

    CreateOrderVO createOrder(OrderAO orderAO);

    OrderModel queryOrderById(long orderId);
}
