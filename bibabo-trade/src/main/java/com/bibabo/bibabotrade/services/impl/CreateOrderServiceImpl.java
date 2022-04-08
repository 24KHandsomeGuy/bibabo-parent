package com.bibabo.bibabotrade.services.impl;

import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.ao.OrderDetailAO;
import com.bibabo.bibabotrade.model.vo.CreateOrderVO;
import com.bibabo.bibabotrade.services.CreateOrderServiceI;
import com.bibabo.order.dto.OrderDetailModel;
import com.bibabo.order.dto.OrderModel;
import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 17:11
 * @description
 */
@Slf4j
@Service
public class CreateOrderServiceImpl implements CreateOrderServiceI {

    @DubboReference(check = false)
    private com.bibabo.order.services.OrderServiceI orderService;


    @Override
    public CreateOrderVO createOrder(OrderAO orderAO) {
        List<OrderDetailAO> orderDetailAOList = orderAO.getOrderDetailAOList();
        List<OrderDetailModel> orderDetailModelList = new ArrayList<>(orderDetailAOList.size());
        for (OrderDetailAO detailAO : orderDetailAOList) {
            OrderDetailModel detailModel = new OrderDetailModel();
            BeanUtils.copyProperties(detailAO, detailModel);
            orderDetailModelList.add(detailModel);
        }

        OrderRequestDTO requestDTO = new OrderRequestDTO();
        OrderModel orderModel = new OrderModel();
        orderAO.setOrderDetailAOList(null);
        BeanUtils.copyProperties(orderAO, orderModel);
        requestDTO.setOrderModel(orderModel);

        OrderResponseDTO responseDTO = orderService.createOrder(requestDTO);
        if (null == responseDTO) {
            return new CreateOrderVO(false, orderAO.getOrderId());
        }
        return new CreateOrderVO(responseDTO.getSuccess(), responseDTO.getOrderId());
    }

    @Override
    public OrderModel queryOrderById(long orderId) {
        return orderService.queryOrderById(orderId);
    }
}
