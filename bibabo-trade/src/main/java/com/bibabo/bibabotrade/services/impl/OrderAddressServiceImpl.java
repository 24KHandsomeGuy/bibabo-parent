package com.bibabo.bibabotrade.services.impl;

import com.bibabo.bibabotrade.services.OrderAddressServiceI;
import com.bibabo.order.dto.OrderAddressInfoDTO;
import com.bibabo.utils.model.ResponseDTO;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author fukuixiang
 * @date 2022/6/7
 * @time 17:17
 * @description
 */
@Service
public class OrderAddressServiceImpl implements OrderAddressServiceI {

    @DubboReference(check = false, timeout = 60 * 1000)
    private com.bibabo.order.services.OrderServiceI orderService;


    @Override
    public ResponseDTO<OrderAddressInfoDTO> queryOrderAddress(long orderId) {
        return orderService.queryOrderAddress(orderId);
    }

    @Override
    public ResponseDTO updateOrderAddress(OrderAddressInfoDTO dto) {
        return orderService.updateOrderAddress(dto);
    }
}
