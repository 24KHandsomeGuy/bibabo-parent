package com.bibabo.bibabomarketingservice.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/25 20:37
 * @Description:
 */
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * Spring容器关闭信号
     */
    private static volatile boolean SPRING_CLOSED_SIGNAL = false;

    public static void springClosed() {
        SPRING_CLOSED_SIGNAL = true;
    }

    public static boolean isSpringClosedSignal() {
        return SPRING_CLOSED_SIGNAL;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

}
