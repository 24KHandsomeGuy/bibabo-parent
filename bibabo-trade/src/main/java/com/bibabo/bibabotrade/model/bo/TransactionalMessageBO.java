package com.bibabo.bibabotrade.model.bo;

import com.bibabo.bibabotrade.model.dto.MessageDTO;
import com.bibabo.order.dto.BaseDTO;
import lombok.Data;
import org.apache.rocketmq.client.producer.LocalTransactionState;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/4/8
 * @time 15:25
 * @description 消息事务包装BO。包装请求参数和响应结果
 */
@Data
public class TransactionalMessageBO<T> extends BaseDTO {

    /**
     * 发送半消息成功后需要执行的业务DTO
     */
    private MessageDTO messageDTO;

    /**
     * 返回给rocketmq的本地事务执行结果
     */
    private LocalTransactionState localTransactionState;

    /**
     * 期待的结果
     */
    private T result;

    /**
     * 期待的结果集合类型
     */
    private List<T> resultList;
}
