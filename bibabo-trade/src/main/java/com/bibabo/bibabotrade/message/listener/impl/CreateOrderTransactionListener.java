package com.bibabo.bibabotrade.message.listener.impl;

import com.bibabo.bibabotrade.message.listener.AbstractTransactionListener;
import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.bo.TransactionalMessageBO;
import com.bibabo.bibabotrade.model.dto.CreateOrderMessageDTO;
import com.bibabo.bibabotrade.model.vo.CreateOrderVO;
import com.bibabo.bibabotrade.services.CreateOrderServiceI;
import com.bibabo.order.dto.OrderModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fukuixiang
 * @date 2022/4/7
 * @time 17:30
 * @description
 */
@Slf4j
@Service("createOrderTransactionListener")
public class CreateOrderTransactionListener extends AbstractTransactionListener {

    @Autowired
    CreateOrderServiceI createOrderService;

    @Override
    protected void executeBusiness(TransactionalMessageBO bo) {
        OrderAO ao = (OrderAO) bo.getMessageDTO();
        CreateOrderVO vo = createOrderService.createOrder(ao);
        // 业务执行结果
        bo.setResult(vo);

        // 需要设置本地事务执行结果给到rocketmq
        if (vo == null || !vo.getSuccess()) {
            bo.setLocalTransactionState(LocalTransactionState.ROLLBACK_MESSAGE);
        } else {
            bo.setLocalTransactionState(LocalTransactionState.COMMIT_MESSAGE);
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CreateOrderMessageDTO dto = objectMapper.readValue(new String(messageExt.getBody()), new TypeReference<CreateOrderMessageDTO>() {
            });
            OrderModel orderModel = createOrderService.queryOrderById(dto.getOrderId());
            if (orderModel != null) {
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return LocalTransactionState.UNKNOW;
    }
}
