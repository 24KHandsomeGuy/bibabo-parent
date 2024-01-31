package com.bibabo.bibabotrade.services;

import com.bibabo.order.dto.OrderAddressInfoDTO;
import com.bibabo.utils.model.ResponseDTO;

/**
 * @author fukuixiang
 * @date 2022/6/7
 * @time 17:17
 * @description
 */
public interface OrderAddressServiceI {

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
