package com.bibabo.bibabotrade.message.listener.impl;

import com.bibabo.bibabotrade.message.listener.AbstractTransactionListener;
import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.bo.TransactionalMessageBO;
import com.bibabo.bibabotrade.model.vo.OrderPayVO;
import com.bibabo.bibabotrade.services.CreateOrderServiceI;
import com.bibabo.bibabotrade.services.PayOrderServiceI;
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
 * @description 订单支付成功mq事务监听回调器
 */
@Slf4j
@Service("orderPaidTransactionListener")
public class OrderPaidTransactionListener extends AbstractTransactionListener {

    @Autowired
    PayOrderServiceI payOrderService;

    @Autowired
    CreateOrderServiceI createOrderService;

    @Override
    protected void executeBusiness(TransactionalMessageBO bo) {
        OrderAO ao = (OrderAO) bo.getMessageDTO();

        OrderPayVO vo = new OrderPayVO(false, ao.getOrderId(), "支付失败");
        try {
            vo = payOrderService.payOrder(ao);
        } catch (RuntimeException e) {
            log.error("支付异常" + e.getMessage(), e);
        }
        // 业务执行结果
        bo.setResult(vo);

        // 需要设置本地事务执行结果给到rocketmq
        if (vo.getSuccess()) {
            bo.setLocalTransactionState(LocalTransactionState.COMMIT_MESSAGE);
        } else {
            bo.setLocalTransactionState(LocalTransactionState.ROLLBACK_MESSAGE);
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        try {
            long orderId = Long.parseLong(new String(messageExt.getBody()));
            OrderModel orderModel = createOrderService.queryOrderById(orderId);
            if (orderModel != null && orderModel.getOrderId() != null && orderModel.getIsPayed() != null && orderModel.getIsPayed() == 1) {
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            return LocalTransactionState.ROLLBACK_MESSAGE;
        } catch (RuntimeException e) {
            return LocalTransactionState.UNKNOW;
        }
    }
}
