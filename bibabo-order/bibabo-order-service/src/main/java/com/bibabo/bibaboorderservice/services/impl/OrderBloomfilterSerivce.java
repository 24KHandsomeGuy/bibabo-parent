package com.bibabo.bibaboorderservice.services.impl;

import com.bibabo.bibaboorderservice.domain.OrderMain;
import com.bibabo.bibaboorderservice.domain.repository.OrderMainRepository;
import com.bibabo.bibaboorderservice.model.enums.RedisPrefixEnum;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fukuixiang
 * @date 2022/6/9
 * @time 16:03
 * @description
 */
@Slf4j
@Service
public class OrderBloomfilterSerivce {


    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private OrderMainRepository orderMainRepository;


    /**
     * 检查订单是否在Bloomfilter中
     *
     * @param orderId
     * @return
     */
    public boolean checkIsInBloomfilter(long orderId) {
        boolean isContains = getOrderBloomfilter().contains(orderId);
        log.info("orderId:{} is in bloomfilter:{} ---> result:{}", orderId, RedisPrefixEnum.ORDER_BLOOMFILTER.getPrefix(), isContains);
        return isContains;
    }

    /**
     * 新创建的order，使用Canal同步到Bloomfilter中
     *
     * @param orderId
     * @return
     */
    public boolean addToBloomfilter(long orderId) {
        boolean addRst = getOrderBloomfilter().add(orderId);
        log.info("orderId:{} add to bloomfilter:{} ---> result:{}", orderId, RedisPrefixEnum.ORDER_BLOOMFILTER.getPrefix(), addRst);
        return addRst;
    }

    private RBloomFilter<Long> getOrderBloomfilter() {
        RBloomFilter<Long> rBloomFilter = redissonClient.getBloomFilter(RedisPrefixEnum.ORDER_BLOOMFILTER.getPrefix());
        boolean initRst = rBloomFilter.tryInit(10000000, 0.03);
        log.info("tryinit bloomfilter:{} ---> result:{}", RedisPrefixEnum.ORDER_BLOOMFILTER.getPrefix(), initRst);
        if (initRst) {
            InitializeOrderBloomfilter();
        }
        return rBloomFilter;
    }

    private void InitializeOrderBloomfilter() {
        long beginTime = System.currentTimeMillis();
        List<OrderMain> orderMainList = orderMainRepository.findAll();
        log.info("OrderBloomfilterSerivce Initialize Order Bloomfilter:{} begin", RedisPrefixEnum.ORDER_BLOOMFILTER.getPrefix());
        orderMainList.forEach(orderMain -> getOrderBloomfilter().add(orderMain.getOrderId()));
        long endTime = System.currentTimeMillis();
        log.info("OrderBloomfilterSerivce Initialize Order Bloomfilter:{} end. total time:{}s", RedisPrefixEnum.ORDER_BLOOMFILTER.getPrefix(), (endTime - beginTime) / 1000);
    }
}
