package com.bibabo.bibabotrade.services.impl;

import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.ao.OrderDetailAO;
import com.bibabo.bibabotrade.model.vo.CreateOrderVO;
import com.bibabo.bibabotrade.services.CreateOrderServiceI;
import com.bibabo.order.dto.OrderDetailModel;
import com.bibabo.order.dto.OrderModel;
import com.bibabo.order.dto.OrderRequestDTO;
import com.bibabo.order.dto.OrderResponseDTO;
import com.bibabo.stock.dto.OccupyStockResponseDTO;
import com.bibabo.stock.dto.StockReqeustDTO;
import com.bibabo.stock.services.StockServiceI;
import com.bibabo.utils.monitor.Profiler;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 17:11
 * @description
 */
@Slf4j
@Service
public class CreateOrderServiceImpl implements CreateOrderServiceI {

    @DubboReference(check = false, timeout = 60 * 1000)
    private com.bibabo.order.services.OrderServiceI orderService;

    @DubboReference(check = false, timeout = 60 * 1000)
    private StockServiceI stockService;

    @Override
    @GlobalTransactional(timeoutMills = 2000, name = "create-order")
    public CreateOrderVO createOrder(OrderAO orderAO) {
        List<OrderDetailAO> orderDetailAOList = orderAO.getOrderDetailAOList();
        List<OrderDetailModel> orderDetailModelList = new ArrayList<>(orderDetailAOList.size());
        List<StockReqeustDTO> stockReqeustDTOList = new ArrayList<>(orderDetailAOList.size());
        for (OrderDetailAO detailAO : orderDetailAOList) {
            OrderDetailModel detailModel = new OrderDetailModel();
            BeanUtils.copyProperties(detailAO, detailModel);
            orderDetailModelList.add(detailModel);

            StockReqeustDTO stockReqeustDTO = new StockReqeustDTO();
            stockReqeustDTO.setOrderId(orderAO.getOrderId());
            stockReqeustDTO.setSkuId(detailAO.getSkuId());
            stockReqeustDTO.setSkuNum(detailAO.getSkuNum());
            stockReqeustDTO.setWareId(detailAO.getWareId());
            stockReqeustDTOList.add(stockReqeustDTO);
        }

        // 先创建订单，检查分布式事务是否生效
        OrderRequestDTO requestDTO = new OrderRequestDTO();
        OrderModel orderModel = new OrderModel();
        orderAO.setOrderDetailAOList(null);
        BeanUtils.copyProperties(orderAO, orderModel);
        requestDTO.setOrderModel(orderModel);
        orderModel.setOrderDetailModelList(orderDetailModelList);
        OrderResponseDTO responseDTO = orderService.createOrder(requestDTO);
        log.info("交易系统创建订单，订单服务调用耗时:{}", Profiler.end());
        if (null == responseDTO || !responseDTO.getSuccess()) {
            throw new RuntimeException("订单创建失败" + responseDTO.getErrorMessage());
        }

        // 占用库存
        OccupyStockResponseDTO occupyStockResponseDTO = stockService.occupyStock(stockReqeustDTOList);
        log.info("交易系统创建订单，库存服务调用耗时:{}", Profiler.end());
        if (occupyStockResponseDTO.getRtnStatus() == -1) {
            throw new RuntimeException("占用库存失败" + occupyStockResponseDTO.getRtnMsg());
        }

        return new CreateOrderVO(responseDTO.getSuccess(), responseDTO.getOrderId(), null);
    }

    @Override
    public OrderModel queryOrderById(long orderId) {
        return orderService.queryOrderById(orderId);
    }
}
