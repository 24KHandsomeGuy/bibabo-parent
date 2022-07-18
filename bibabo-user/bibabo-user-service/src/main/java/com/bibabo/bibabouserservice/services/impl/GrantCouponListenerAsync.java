package com.bibabo.bibabouserservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/18 10:27
 * @Description:
 */
@Component
@Slf4j
public class GrantCouponListenerAsync {

    @EventListener(GrantCouponEventAsync.class)
    @Async
    public void sendEmail(GrantCouponEventAsync event) {
        long custId = event.getCustId();
        long couponId = event.getCouponId();
        log.info("线程:{}，email:尊敬的用户{}您好，已发送优惠劵{}到您的账户，祝购物愉快", Thread.currentThread().getName(), custId, couponId);
    }
}
