package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.bibaboorderservice.domain.OrderDetail;
import com.bibabo.bibaboorderservice.domain.OrderMain;
import com.bibabo.bibaboorderservice.domain.repository.OrderDetailRepository;
import com.bibabo.bibaboorderservice.domain.repository.OrderMainRepository;
import com.bibabo.bibaboorderservice.services.OrderMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/5/9
 * @time 18:56
 * @description
 */
@Service
@Slf4j
public class OrderMainServiceImpl implements OrderMainService {

    @Autowired
    private OrderMainRepository orderMainRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderMain saveOrderAndDetail(OrderMain orderMain, List<OrderDetail> orderDetailList) {
        OrderMain rstOm = orderMainRepository.save(orderMain);
        List<OrderDetail> rstOdList = orderDetailRepository.saveAll(orderDetailList);
        rstOm.setOrderDetailList(rstOdList);

        return rstOm;
    }
}
