package com.bibabo.bibabotrade.controller;

import com.bibabo.bibabotrade.message.sender.MessageSenderService;
import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.bo.TransactionalMessageBO;
import com.bibabo.bibabotrade.model.dto.CreateOrderMessageDTO;
import com.bibabo.bibabotrade.model.vo.CreateOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 17:36
 * @Description:
 */
@RestController
@Slf4j
public class OrderController {

    private static final AtomicLong ORDER_ID = new AtomicLong(1);


    @Autowired
    MessageSenderService messageSender;

    @PostMapping("/order")
    public CreateOrderVO createOrder(@RequestBody OrderAO orderAO) {
        log.info("交易系统接收前端下单请求参数：{} ", orderAO);

        CreateOrderVO vo = new CreateOrderVO(false, null);
        // 组装创建订单业务DTO。获取订单号、填充时间
        Date now = new Date();
        Long orderId = ORDER_ID.getAndIncrement();
        orderAO.setOrderId(orderId);
        orderAO.setCreateDate(now);
        // 业务逻辑执行BO
        TransactionalMessageBO<CreateOrderVO> bo = new TransactionalMessageBO();
        bo.setMessageDTO(orderAO);

        // 组装发送短信消息DTO
        CreateOrderMessageDTO messageDTO = CreateOrderMessageDTO.builder().createDate(now).custTelephone(orderAO.getCustTelephone()).orderId(orderId).build();
        // 发送事务消息并回调执行本地事务处理
        boolean rst = messageSender.sendCreateOrderSmsTransactionalMsg(messageDTO, bo);
        if (rst) {
            vo = bo.getResult();
        }

        log.info("交易系统接收前端下单请求参数：{} 响应结果：{}", orderAO, vo);
        return vo;
    }

    @GetMapping("/order")
    public String listOrderByOrderIds(@RequestParam List<Long> orderIds) {
        return null;
    }
}
