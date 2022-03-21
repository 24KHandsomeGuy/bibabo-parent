package com.bibabo.bibabotrade.controller;

import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;
import com.bibabo.order.services.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 17:36
 * @Description:
 */
@RestController
@Slf4j
public class OrderController {

    @Reference
    private OrderServiceI orderService;

    @PostMapping("/order")
    public String createOrder(@RequestBody OrderAO orderAO) {
        log.info("交易系统接收前端下单请求参数：{} ", orderAO);
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        OrderResponseDTO responseDTO = orderService.createOrder(requestDTO);
        log.info("交易系统接收前端下单请求参数：{} 响应结果：{}", orderAO, responseDTO);
        return "SUCCESS";
    }

    @GetMapping("/order")
    public String listOrderByOrderIds(@RequestParam List<Long> orderIds) {
        return null;
    }
}
