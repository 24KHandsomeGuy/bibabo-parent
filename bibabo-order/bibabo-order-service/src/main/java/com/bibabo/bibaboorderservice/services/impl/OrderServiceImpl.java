package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.order.dto.OrderDetailModel;
import com.bibabo.order.dto.OrderModel;
import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;
import com.bibabo.order.services.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 20:37
 * @Description:
 */
@DubboService
@Slf4j
public class OrderServiceImpl implements OrderServiceI {

    private static final ConcurrentHashMap<Long, OrderModel> ORDER_CONTAINER = new ConcurrentHashMap<>();

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        log.info("接收请求 {}", dto);
        OrderResponseDTO responseDTO = new OrderResponseDTO(true, dto.getOrderModel().getOrderId());
        BeanUtils.copyProperties(dto, dto.getOrderModel());

        List<OrderDetailModel> orderDetailModelList = dto.getOrderModel().getOrderDetailModelList();
        CompletableFuture.supplyAsync(() -> {
            List<Long> skuIdList = new ArrayList<>();
            orderDetailModelList.forEach(od -> {
                    skuIdList.add(od.getSkuId());
                }
            );
            return skuIdList;
        }).thenAcceptAsync(skuIdList -> {
            // TODO 调用主数据获取商品原价和销项税
            orderDetailModelList.forEach(od -> {
                // 调用系统获取
                od.setSalePrice(BigDecimal.ONE);
            });
        });

        ORDER_CONTAINER.putIfAbsent(dto.getOrderModel().getOrderId(), dto.getOrderModel());
        log.info("创建订单成功");
        return responseDTO;
    }

    @Override
    public OrderModel queryOrderById(long orderId) {
        return ORDER_CONTAINER.get(orderId);
    }
}
