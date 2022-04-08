package com.bibabo.bibabotrade.message.listener;

import com.bibabo.bibabotrade.model.bo.TransactionalMessageBO;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;

import java.util.InputMismatchException;

/**
 * @author fukuixiang
 * @date 2022/4/8
 * @time 15:39
 * @description
 */
public abstract class AbstractTransactionListener implements TransactionListener {

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        if (!(arg instanceof TransactionalMessageBO)) {
            throw new InputMismatchException("类型不匹配异常");
        }
        TransactionalMessageBO bo = (TransactionalMessageBO) arg;
        executeBusiness(bo);
        return bo.getLocalTransactionState();
    }

    /**
     * 执行业务逻辑
     * 如果存在结果会填充到bo.result
     *
     * @param bo
     */
    protected abstract void executeBusiness(TransactionalMessageBO bo);
}
