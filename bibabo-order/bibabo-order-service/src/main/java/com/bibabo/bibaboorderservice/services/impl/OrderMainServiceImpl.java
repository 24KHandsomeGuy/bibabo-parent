package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.bibaboorderservice.domain.OrderDetail;
import com.bibabo.bibaboorderservice.domain.OrderMain;
import com.bibabo.bibaboorderservice.domain.repository.OrderDetailRepository;
import com.bibabo.bibaboorderservice.domain.repository.OrderMainRepository;
import com.bibabo.bibaboorderservice.model.enums.OrderStatusEnum;
import com.bibabo.bibaboorderservice.services.OrderMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 18:56
 * @description
 */
@Service
@Slf4j
public class OrderMainServiceImpl implements OrderMainService {

    @Autowired
    private OrderMainRepository orderMainRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderMain saveOrderAndDetail(OrderMain orderMain, List<OrderDetail> orderDetailList) {
        OrderMain rstOm = orderMainRepository.save(orderMain);
        List<OrderDetail> rstOdList = orderDetailRepository.saveAll(orderDetailList);
        rstOm.setOrderDetailList(rstOdList);

        return rstOm;
    }

    @Override
    public OrderMain findByOrderId(long orderId) {
        return orderMainRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public int updateOrderStatusCancel(long orderId) {
        return orderMainRepository.updateOrderStatusCancel(orderId, OrderStatusEnum.ORDER_CANCEL.getStatus(), OrderStatusEnum.AMOUNT_PAID.getStatus());
    }

    @Override
    @Transactional
    public int updateOrderIsPayed(long orderId) {
        return orderMainRepository.updateOrderIsPayed(orderId, OrderStatusEnum.AMOUNT_PAID.getStatus(), OrderStatusEnum.AMOUNT_PAID.getStatus(), 1);
    }

    @Override
    @Transactional
    public int updateOrderAddress(long orderId, String custAddress) {
        return orderMainRepository.updateOrderAddress(orderId, custAddress);
    }

}
