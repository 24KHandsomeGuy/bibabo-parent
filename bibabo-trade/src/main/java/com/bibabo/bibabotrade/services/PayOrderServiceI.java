package com.bibabo.bibabotrade.services;

import com.bibabo.bibabotrade.model.ao.OrderAO;
import com.bibabo.bibabotrade.model.vo.OrderPayVO;

/**
 * @author fukuixiang
 * @date 2022/5/7
 * @time 15:50
 * @description
 */
public interface PayOrderServiceI {
    
    OrderPayVO payOrder(OrderAO orderAO);
}
