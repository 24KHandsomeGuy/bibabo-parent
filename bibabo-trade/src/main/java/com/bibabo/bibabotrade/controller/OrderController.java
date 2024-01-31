package com.bibabo.bibabotrade.controller;

import com.bibabo.bibabotrade.message.sender.MessageSenderService;
import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.bo.TransactionalMessageBO;
import com.bibabo.bibabotrade.model.vo.CreateOrderVO;
import com.bibabo.bibabotrade.model.vo.OrderPayVO;
import com.bibabo.bibabotrade.services.CreateOrderServiceI;
import com.bibabo.bibabotrade.services.OrderAddressServiceI;
import com.bibabo.bibabotrade.services.queue.QueueManager;
import com.bibabo.bibabotrade.utils.NumberGenerator;
import com.bibabo.bibabotrade.utils.RedisRateLimiterEnum;
import com.bibabo.order.dto.OrderAddressInfoDTO;
import com.bibabo.order.dto.OrderModel;
import com.bibabo.utils.model.ResponseDTO;
import com.bibabo.utils.monitor.Profiler;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 17:36
 * @Description:
 */
@RestController
@Slf4j
public class OrderController {


    @Autowired
    private NumberGenerator numberGenerator;

    @Autowired
    private CreateOrderServiceI createOrderService;

    @Autowired
    private MessageSenderService messageSender;

    @Autowired
    private OrderAddressServiceI orderAddressService;

    @Autowired
    private RedissonClient redissonClient;


    @PostMapping("/order")
    public CreateOrderVO createOrder(@RequestBody OrderAO orderAO) {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(RedisRateLimiterEnum.CREATE_ORDER.getResource());
        boolean limitRst = rateLimiter.tryAcquire();
        if (!limitRst) {
            log.info("createOrder rate limit:{}", limitRst);
            return new CreateOrderVO(false, null, "创建失败，被限流");
        }

        Profiler.begin();
        log.info("交易系统接收前端下单请求参数：{} ", orderAO);

        // 组装创建订单业务DTO。获取订单号、填充时间
        Date now = new Date();
        long orderId = numberGenerator.generateOrderId();
        orderAO.setOrderId(orderId);
        orderAO.setCreateDate(now);

        // 上报日志
        QueueManager.getReportLogQueue().add(orderAO);
        log.info("交易系统创建订单前置准备耗时:{}", Profiler.end());

        CreateOrderVO vo = new CreateOrderVO(false, orderId, "创建失败");
        // 创建订单，多服务中、任一服务失败则抛出异常，全局分布式事务回滚（创建订单使用强一致事务）
        try {
            vo = createOrderService.createOrder(orderAO);
        } catch (RuntimeException e) {
            log.error(String.format("订单号%d，创建订单失败%s", orderId, e.getMessage()), e);
            vo.setErrorMsg("创建失败：" + e.getMessage());
        }

        // 上报日志
        QueueManager.getReportLogQueue().add(vo);

        // 创建成功发送一条支付超时检查消息，支付超时需要自动取消订单
        // 在这个地方发送消息，可能会存在消息的丢失，所以作为补偿，还需要有一个job定时抓取支付超时订单取消掉
        if (vo.getSuccess()) {
            messageSender.sendPaymentTimeOutCheckMsg(orderId);
        }

        log.info("交易系统创建订单总耗时:{}", Profiler.end());

        log.info("交易系统接收前端下单请求参数：{} 响应结果：{}", orderAO, vo);
        return vo;
    }


    @GetMapping("/order")
    public List<OrderModel> listOrderByOrderIds(@RequestParam List<Long> orderIds) {
        List<OrderModel> orderModelList = new ArrayList<>(orderIds.size());
        orderIds.forEach(orderId -> {
            OrderModel orderModel = createOrderService.queryOrderById(orderId);
            orderModelList.add(orderModel);
        });
        return orderModelList;
    }


    @PutMapping("/order")
    public OrderPayVO payOrder(@RequestBody OrderAO orderAO) {
        log.info("交易系统接收前端支付请求参数：{} ", orderAO);

        OrderPayVO vo = new OrderPayVO(false, null, null);
        if (orderAO.getOrderId() == null) {
            vo.setErrorMsg(String.format("参数校验，orderId不可以为空%s", orderAO));
            return vo;
        }

        // 业务逻辑执行BO
        TransactionalMessageBO<OrderPayVO> bo = new TransactionalMessageBO<>();
        bo.setMessageDTO(orderAO);
        boolean sendRst = messageSender.sendPayedOrderTransactionalMsg(orderAO.getOrderId(), bo);
        if (sendRst && bo.getResult() != null) {
            vo = bo.getResult();
        }

        log.info("交易系统接收前端下单请求参数：{} 响应结果：{}", orderAO, vo);
        return vo;
    }


    @GetMapping("/order/address")
    public ResponseDTO getOrderAddress(@RequestParam long orderId) {
        log.info("交易系统接收前端查询订单地址请求参数：{} ", orderId);
        ResponseDTO responseDTO = orderAddressService.queryOrderAddress(orderId);
        log.info("交易系统接收前端查询订单地址请求参数：{} 响应结果：{}", orderId, responseDTO);
        return responseDTO;
    }


    @PutMapping("/order/address")
    public ResponseDTO updateOrderAddress(@RequestBody OrderAO orderAO) {
        log.info("交易系统接收前端修改订单地址请求参数：{} ", orderAO);

        // 懒得转VO了
        if (orderAO.getOrderId() == null) {
            return ResponseDTO.builder().fail(String.format("参数校验，orderId不可以为空%s", orderAO)).build();
        }

        OrderAddressInfoDTO dto = new OrderAddressInfoDTO();
        dto.setOrderId(orderAO.getOrderId());
        dto.setCustAddress(orderAO.getAddress());
        ResponseDTO responseDTO = orderAddressService.updateOrderAddress(dto);
        log.info("交易系统接收前端修改订单地址请求参数：{} 响应结果：{}", orderAO, responseDTO);
        return responseDTO;
    }

}
