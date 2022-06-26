package com.bibabo.bibabotrade.common;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/25 22:50
 * @Description: TODO Runtime#exit()或者Runtime#shutdown()退出，会执行hook
 * Spring 容器会先关闭掉容器，再调用Runtime#shutdown()，所以如果我们需要依赖Spring做善后的业务工作，是不可以把退出信号交给jvm hook处理的，因为已经晚了
 * 改为Spring容器销毁监听处理
 */
@Slf4j
public class SystemCustomerShutdownHook extends Thread {

    private final AtomicBoolean registered = new AtomicBoolean(false);

    private static final SystemCustomerShutdownHook SYSTEM_CUSTOMER_SHUTDOWN_HOOK = new SystemCustomerShutdownHook("systemCustomerShutdownHook");

    private SystemCustomerShutdownHook(String name) {
        super(name);
    }

    public static SystemCustomerShutdownHook getSystemCustomerShutdownHook() {
        return SYSTEM_CUSTOMER_SHUTDOWN_HOOK;
    }

    @Override
    public void run() {
        log.info("SystemCustomerShutdownHook execute start");
        doModifyJVMExitSignal();
        log.info("SystemCustomerShutdownHook execute end");
    }

    private void doModifyJVMExitSignal() {
        JvmExitSignalContext.JVM_EXIT_SIGNAL = true;
    }

    public void register() {
        if (registered.compareAndSet(false, true)) {
            Runtime.getRuntime().addShutdownHook(getSystemCustomerShutdownHook());
        }
    }

}
