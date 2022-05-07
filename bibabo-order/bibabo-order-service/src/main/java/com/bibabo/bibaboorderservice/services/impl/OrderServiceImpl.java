package com.bibabo.bibaboorderservice.services.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.bibabo.order.dto.OrderModel;
import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;
import com.bibabo.order.services.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 20:37
 * @Description:
 */
@DubboService
@Slf4j
public class OrderServiceImpl implements OrderServiceI {

    public static final ConcurrentHashMap<Long, OrderModel> ORDER_CONTAINER = new ConcurrentHashMap<>();

    @Value("${nacos.name}")
    private String name;

    @PostConstruct
    public void init() {
        log.info("");
    }

    @Override
    @SentinelResource(value = "flow-create-order", fallback = "createOrderFallBack")
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        log.info("接单服务接收请求 {}", dto);
        OrderResponseDTO responseDTO = new OrderResponseDTO(true, dto.getOrderModel().getOrderId(), null);
        ORDER_CONTAINER.putIfAbsent(dto.getOrderModel().getOrderId(), dto.getOrderModel());
        log.info("创建订单成功");
        return responseDTO;
    }

    public OrderResponseDTO createOrderFallBack(OrderRequestDTO dto) {
        log.error(String.format("createOrderFallBack限流，创建订单orderId:%d 失败", dto.getOrderModel().getOrderId()));
        return new OrderResponseDTO(false, dto.getOrderModel().getOrderId(), "createOrderFallBack限流");
    }

    @Override
    @SentinelResource(value = "flow-query-order", fallback = "queryOrderFailBack")
    public OrderModel queryOrderById(long orderId) {
        return ORDER_CONTAINER.get(orderId) == null ? new OrderModel() : ORDER_CONTAINER.get(orderId);
    }

    public OrderModel queryOrderFailBack(long orderId) {
        log.error(String.format("queryOrderById限流，获取订单orderId:%d 失败", orderId));
        return new OrderModel();
    }

    /*@Override
    @SentinelResource(value = "degrade-query-order", fallback = "queryOrderCircuitBreak")
    public OrderModel queryOrderById(long orderId) {
        if (ORDER_CONTAINER.get(orderId) == null) {
            throw new RuntimeException("queryOrderById出现了异常");
        }
        return ORDER_CONTAINER.get(orderId);
    }

    public OrderModel queryOrderCircuitBreak(long orderId) {
        log.error(String.format("queryOrderById熔断，获取订单orderId:%d 失败", orderId));
        return new OrderModel();
    }*/

    @Override
    public OrderResponseDTO payOrder(long orderId) {
        if (ORDER_CONTAINER.get(orderId) == null) {
            return new OrderResponseDTO(false, orderId, "订单不存在");
        }
        OrderModel orderModel = ORDER_CONTAINER.get(orderId);
        orderModel.setIsPayed((short) 1);
        return new OrderResponseDTO(true, orderId, "支付成功");
    }
}
