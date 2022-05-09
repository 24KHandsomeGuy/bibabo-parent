package com.bibabo.bibaboorderservice.services.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.bibabo.bibaboorderservice.domain.OrderDetail;
import com.bibabo.bibaboorderservice.domain.OrderMain;
import com.bibabo.bibaboorderservice.model.enums.OrderStatusEnum;
import com.bibabo.bibaboorderservice.services.OrderMainService;
import com.bibabo.order.dto.OrderDetailModel;
import com.bibabo.order.dto.OrderModel;
import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;
import com.bibabo.order.services.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    /*@Value("${nacos.name}")
    private String name;

    @PostConstruct
    public void init() {
        log.info("");
    }*/

    @Autowired
    private OrderMainService orderMainService;

    @Override
    @SentinelResource(value = "flow-create-order", fallback = "createOrderFallBack")
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        log.info("接单服务接收请求 {}", dto);
        OrderResponseDTO responseDTO = new OrderResponseDTO(true, dto.getOrderModel().getOrderId(), null);
        OrderModel orderModel = dto.getOrderModel();
        OrderMain orderMain = buildOrderMain(orderModel);
        List<OrderDetail> orderDetailList = buildOrderDetailList(orderModel);
        OrderMain rstOm = orderMainService.saveOrderAndDetail(orderMain, orderDetailList);
        if (rstOm != null) {
            log.info("创建订单成功orderId:{}", orderModel.getOrderId());
            return responseDTO;
        }
        log.info("创建订单失败orderId:{}", orderModel.getOrderId());
        return responseDTO;
    }

    private OrderMain buildOrderMain(OrderModel orderModel) {
        Date now = new Date();
        return OrderMain.builder()
                .orderId(orderModel.getOrderId())
                .orderStatus(OrderStatusEnum.WAIT_FOR_PAY.getStatus())
                .isFinish(0)
                .wareId(1)
                .custId(1L)
                .custName("Damon")
                .custMobile("18511111111")
                .payMoney(BigDecimal.ZERO)
                .totalPrice(BigDecimal.ZERO)
                .couponPrice(BigDecimal.ZERO)
                .deliveryPrice(BigDecimal.ZERO)
                .isPayed(0)
                .province("北京")
                .city("北京市")
                .county("朝阳区")
                .custAddress("三里屯")
                .orderDate(now)
                .bookDate(now)
                .createDate(now)
                .build();
    }

    private List<OrderDetail> buildOrderDetailList(OrderModel orderModel) {
        List<OrderDetailModel> orderDetailModelList = orderModel.getOrderDetailModelList();
        List<OrderDetail> orderDetailList = new ArrayList<>(orderDetailModelList.size());
        Date now = new Date();
        orderDetailModelList.forEach(odm ->
                orderDetailList.add(OrderDetail.builder()
                        .orderId(orderModel.getOrderId())
                        .lineNo(1)
                        .skuId(odm.getSkuId())
                        .skuName(odm.getSkuName())
                        .wareId(1)
                        .skuNum(odm.getSkuNum())
                        .skuPrice(BigDecimal.ZERO)
                        .salePrice(BigDecimal.ZERO)
                        .isGift(1)
                        .createDate(now)
                        .bookDate(now)
                        .build()));
        return orderDetailList;
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
