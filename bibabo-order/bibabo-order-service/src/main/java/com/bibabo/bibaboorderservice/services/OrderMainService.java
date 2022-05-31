package com.bibabo.bibaboorderservice.services;

import com.bibabo.bibaboorderservice.domain.OrderDetail;
import com.bibabo.bibaboorderservice.domain.OrderMain;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 18:55
 * @description
 */
public interface OrderMainService {

    OrderMain saveOrderAndDetail(OrderMain orderMain, List<OrderDetail> orderDetailList);

    OrderMain findByOrderId(long orderId);

    int updateOrderStatusCancel(long orderId);

    int updateOrderIsPayed(long orderId);
}
