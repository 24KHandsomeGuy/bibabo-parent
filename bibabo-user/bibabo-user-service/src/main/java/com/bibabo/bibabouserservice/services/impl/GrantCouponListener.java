package com.bibabo.bibabouserservice.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/18 10:19
 * @Description:
 */
@Slf4j
@Component
public class GrantCouponListener implements ApplicationListener<GrantCouponEvent> {

    @Override
    public void onApplicationEvent(GrantCouponEvent event) {
        long custId = event.getCustId();
        long couponId = event.getCouponId();
        log.info("线程:{}，message:尊敬的用户{}您好，已发送优惠劵{}到您的账户，祝购物愉快", Thread.currentThread().getName(), custId, couponId);
    }
}
