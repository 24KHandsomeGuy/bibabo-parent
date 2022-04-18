package com.bibabo.utils.thread.threadfactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fukuixiang
 * @date 2022/3/29
 * @time 13:51
 * @description
 */
public class CustomizableThreadFactory implements ThreadFactory {

    private static final String THREAD_GROUP_NAME = "CustomizableThreadGroup";

    private AtomicInteger thread_num = new AtomicInteger();

    private String threadNamePrefix = THREAD_GROUP_NAME;

    public CustomizableThreadFactory() {}

    public CustomizableThreadFactory(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        /*SecurityManager s = System.getSecurityManager();
        ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();*/
        ThreadGroup tg = new ThreadGroup(THREAD_GROUP_NAME);
        String threadName = threadNamePrefix + "-Thread-" + thread_num.incrementAndGet();

        Thread t = new Thread(tg, r, threadName, 1024);
        t.setPriority(Thread.MAX_PRIORITY);
        if (t.isDaemon()) {
            // 业务线程不能为守护线程
            t.setDaemon(false);
        }
        return t;
    }
}
