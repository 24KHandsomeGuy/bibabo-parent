package com.bibabo.bibabotrade.services.impl;

import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.vo.OrderPayVO;
import com.bibabo.bibabotrade.services.PayOrderServiceI;
import com.bibabo.order.dto.OrderResponseDTO;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author fukuixiang
 * @date 2022/5/7
 * @time 15:51
 * @description
 */
@Service
public class PayOrderServiceImpl implements PayOrderServiceI {

    @DubboReference(check = false, timeout = 60 * 1000)
    private com.bibabo.order.services.OrderServiceI orderService;

    @Override
    @GlobalTransactional(timeoutMills = 2000, name = "pay-order")
    public OrderPayVO payOrder(OrderAO orderAO) {
        OrderResponseDTO dto = orderService.payOrder(orderAO.getOrderId());
        // TODO 通知支付系统和订单系统，都成功才处理订单
        if (dto.getSuccess()) {
            return new OrderPayVO(true, orderAO.getOrderId(), null);
        }
        return new OrderPayVO(false, orderAO.getOrderId(), dto.getErrorMessage());
    }
}
