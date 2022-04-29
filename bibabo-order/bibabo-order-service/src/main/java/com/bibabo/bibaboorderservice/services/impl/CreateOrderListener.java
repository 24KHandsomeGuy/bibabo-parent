package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.bibaboorderservice.model.enums.SkuTypeEnum;
import com.bibabo.order.dto.OrderDetailModel;
import com.bibabo.order.dto.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fukuixiang
 * @date 2022/4/13
 * @time 17:21
 * @description
 */
@Service
@Slf4j
public class CreateOrderListener {

    @Autowired
    private BBBThreadPoolExecutor bbbThreadPoolExecutor;

    @Autowired
    private MessageSenderService messageSenderService;


    @StreamListener("inputCreateOrder")
    public void receiveInputCreateOrderMsg(@Payload long orderId) {

        log.info(String.format("receiveInputCreateOrderMsg receive message %s", orderId));

        ThreadPoolExecutor executor = bbbThreadPoolExecutor.getExecutor();

        OrderModel omDb = OrderServiceImpl.ORDER_CONTAINER.get(orderId);
        Optional.ofNullable(omDb).ifPresent(om -> {
            List<OrderDetailModel> orderDetailModelList = om.getOrderDetailModelList();

            OrderModel omMsg = new OrderModel();
            omMsg.setOrderDetailModelList(null);
            BeanUtils.copyProperties(om, omMsg);

            // 1.0 发送生单成功短息发送消息
            CompletableFuture.runAsync(() -> {
                log.info(String.format("发送生单成功、发送短信消息，消息%s", omMsg));
                boolean rst = messageSenderService.sendCreateOrder2SmsMsg(omMsg);
                log.info(String.format("发送生单成功、发送短信消息，消息%s，结果%b", om, rst));
            }, executor);

            // 1.1 调用主数据填充虚拟组套子商品
            CompletableFuture<List<OrderModel>> splitOrderCompletableFuture = CompletableFuture.runAsync(() -> {

                orderDetailModelList.forEach(od -> {
                    if (od.getSkuType() == SkuTypeEnum.VIRTUAL_SUIT.getType()) {

                    }
                });
            }, executor).thenRun(() -> {// 1.2 调用主数据获取商品原价和销项税
                List<Long> skuIdList = new ArrayList<>();
                orderDetailModelList.forEach(od -> {
                            skuIdList.add(od.getSkuId());
                        }
                );

                orderDetailModelList.forEach(od -> {
                    // 调用系统获取
                    od.setSkuPrice(BigDecimal.ONE);
                });
            }).thenRun(() -> {// 1.3 分摊
                // do nothing
            }).thenRun(() -> {// 1.4 订单的附赠

                om.getCustId();
            }).thenApply(Void -> {// TODO 1.5 订单的拆分
                List<OrderModel> orderModelList = new ArrayList<>();
                return orderModelList;
            });

            // TODO 2.1 分配承运商
            splitOrderCompletableFuture.thenApplyAsync(orderModelList -> {

                return orderModelList;
            }, executor).thenApply(orderModelList -> {// TODO 2.2 分配erp

                return orderModelList;
            }).thenAccept(orderModelList -> {// TODO 2.3 下发WMS
                orderModelList.forEach(orderModel -> {

                });
            });

            // TODO 3.1 生成发票
            splitOrderCompletableFuture.thenAcceptAsync(orderModelList -> {

            }, executor);
        });
    }

}
