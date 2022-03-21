package com.bibabo.bibaboorderservice.services;

import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;
import com.bibabo.order.services.OrderServiceI;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/20 20:37
 * @Description:
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderServiceI {
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        log.info("接收请求 {}", dto);
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        return responseDTO;
    }
}
