package com.bibabo.bibabomarketingservice.listener;

import com.alibaba.spring.context.OnceApplicationContextEventListener;
import com.bibabo.bibabomarketingservice.message.sender.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/25 20:10
 * @Description:
 */
@Component
public class SpringContextClosedListener extends OnceApplicationContextEventListener implements Ordered {


    @Autowired
    MessageSenderService messageSenderService;

    @Override
    public int getOrder() {
        // Dubbo断开注册中心后关闭
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    protected void onApplicationContextEvent(ApplicationContextEvent event) {
        if (event instanceof ContextClosedEvent) {
            System.out.println("监听到Spring关闭:" + event.getSource());
            messageSenderService.sendMarketingMsg(10086L);
        }
    }
}
