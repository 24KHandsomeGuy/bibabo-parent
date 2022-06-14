package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.bibaboorderservice.domain.OrderDetail;
import com.bibabo.bibaboorderservice.domain.OrderMain;
import com.bibabo.bibaboorderservice.services.OrderMainService;
import com.bibabo.bibaboorderservice.services.timewheel.GrantOverTimeCouponTask;
import com.bibabo.bibaboorderservice.util.spring.SpringBeanUtils;
import com.bibabo.bibaboorderservice.util.timewheel.WheelTimerFactory;
import com.bibabo.order.dto.OrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fukuixiang
 * @date 2022/4/13
 * @time 17:21
 * @description
 */
@Service
@Slf4j
public class OrderListener {

    @Autowired
    private BBBThreadPoolExecutor bbbThreadPoolExecutor;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private OrderMainService orderMainService;


    @StreamListener("inputOrderPaid")
    public void receiveInputOrderPaidMsg(@Payload long orderId) {

        log.info(String.format("receiveInputOrderPaidMsg receive message %d", orderId));

        ThreadPoolExecutor executor = bbbThreadPoolExecutor.getExecutor();

        OrderMain omDb = orderMainService.findByOrderId(orderId);
        Optional.ofNullable(omDb).ifPresent(om -> {
            List<OrderDetail> orderDetailList = om.getOrderDetailList();

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

                orderDetailList.forEach(od -> {
                    /*if (od.getSkuType() == SkuTypeEnum.VIRTUAL_SUIT.getType()) {

                    }*/
                });
            }, executor).thenRun(() -> {// 1.2 调用主数据获取商品原价和销项税
                List<Long> skuIdList = new ArrayList<>();
                orderDetailList.forEach(od -> {
                            skuIdList.add(od.getSkuId());
                        }
                );

                orderDetailList.forEach(od -> {
                    // 调用系统获取
                    od.setSkuPrice(BigDecimal.ONE);
                });
            }).thenRun(() -> {// 1.3 分摊
                // do nothing
            }).thenRun(() -> {// 1.4 订单的附赠

                om.getCustId();
            }).thenApply(Void -> {// TODO 1.5 订单的拆分
                List<OrderModel> orderModelList = new ArrayList<>();
                orderModelList.add(omMsg);
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

            // 4.1 将配送超时随机赠送优惠券写入时间轮，超时发放劵
            splitOrderCompletableFuture.thenAcceptAsync(orderModelList -> {
                orderModelList.forEach(omm -> {
                    GrantOverTimeCouponTask task = null;
                    try {
                        task = SpringBeanUtils.<GrantOverTimeCouponTask>getBean("grantOverTimeCouponTask", GrantOverTimeCouponTask.class);
                    } catch (RuntimeException e) {
                        log.error("", e);
                    }

                    task.setOrderId(omm.getOrderId());
                    task.setCustId(omm.getCustId());
                    long delay = omm.getExpectShipmentTime().getTime() - new Date().getTime();
                    WheelTimerFactory.FIVE_SECONDS_WHEEL_TIMER.newTimeout(task, delay, TimeUnit.MILLISECONDS);
                });
            });
        });
    }


    @StreamListener("inputPaymentTimeOutCheck")
    public void receiveInputPaymentTimeOutCheckMsg(@Payload long orderId) {
        log.info(String.format("receiveInputPaymentTimeOutCheckMsg receive message %d", orderId));

        OrderMain om = orderMainService.findByOrderId(orderId);
        if (om == null) {
            log.error(String.format("receiveInputPaymentTimeOutCheckMsg orderId:%d non existent", orderId));
            return;
        }
        if (om.getIsPayed() != null && om.getIsPayed() == 1) {
            log.info(String.format("receiveInputPaymentTimeOutCheckMsg orderId:%d already paid", orderId));
            return;
        }
        log.info(String.format("receiveInputPaymentTimeOutCheckMsg orderId:%d no payment, about to cancel. ", orderId));
        int rst = orderMainService.updateOrderStatusCancel(orderId);
        log.info(String.format("receiveInputPaymentTimeOutCheckMsg orderId:%d no payment, about to cancel. cancel result:%d ", orderId, rst));
    }
}
