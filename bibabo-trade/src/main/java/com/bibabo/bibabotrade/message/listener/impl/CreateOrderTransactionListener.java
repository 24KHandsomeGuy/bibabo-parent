package com.bibabo.bibabotrade.message.listener.impl;

import com.bibabo.bibabotrade.message.listener.AbstractTransactionListener;
import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.bo.TransactionalMessageBO;
import com.bibabo.bibabotrade.model.vo.CreateOrderVO;
import com.bibabo.bibabotrade.services.CreateOrderServiceI;
import com.bibabo.order.dto.OrderModel;
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
        try {
            long orderId = Long.parseLong(new String(messageExt.getBody()));
            OrderModel orderModel = createOrderService.queryOrderById(orderId);
            if (orderModel != null) {
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } catch (RuntimeException e) {
            return LocalTransactionState.UNKNOW;
        }
        /*try {
            ObjectMapper objectMapper = new ObjectMapper();
            CreateOrderMessageDTO dto = objectMapper.readValue(new String(messageExt.getBody()), new TypeReference<CreateOrderMessageDTO>() {
            });

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/
    }
}
