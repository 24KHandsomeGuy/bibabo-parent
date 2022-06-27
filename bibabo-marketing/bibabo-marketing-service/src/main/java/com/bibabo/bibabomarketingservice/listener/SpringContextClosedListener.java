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
 * @Description: TODO 此处痛点，1.无法保证该节点在Dubbo服务从注册中心上下线掉之后处理 2.RocketMq如何保证本机不消费？因为消费和生产的channel没有关闭
 *
 *  解决办法：
 *  2.把该节点放到rocketmq消费和生产都关闭之后，再重新使用RocketMq的javaApi建立连接，发送消息
 *
 *  AbstractApplicationContext#doClose()
 *  先publishEvent(new ContextClosedEvent(this)); Dubbo是在此处关闭
 *  后this.lifecycleProcessor.onClose(); String Cloud Stream是在此处关闭
 *
 *  InputBindingLifecycle Integer.MAX_VALUE - 1000;、OutputBindingLifecycle Integer.MIN_VALUE + 1000;
 *  DefaultLifecycleProcessor#stopBeans()
 *  keys.sort(Collections.reverseOrder());
* 			for (Integer key : keys) {
* 				phases.get(key).stop();
*           }
 *  Phased接口的public int getPhase() {倒叙执行，值越大越优先执行
 *  InputBindingLifecycle先执行 OutputBindingLifecycle后执行
 *  那我们配置的LifeCycle的Phase值只要介于之间，既可以等待Input关闭，Output还未关闭，直接使用Output发消息
 * @see
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
            // messageSenderService.sendMarketingMsg(10086L);
        }
    }
}
