package com.bibabo.utils.thread.rejectpolicy;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fukuixiang
 * @date 2021/8/13
 * @time 17:45
 * @description 创建一个新线程去执行策略
 */
public class NewThreadExecutePolicy implements RejectedExecutionHandler {

    private static final AtomicLong newThreadCntAtomic = new AtomicLong(1);

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        new Thread(r, "NewThreadExecutePolicy" + newThreadCntAtomic.get()).start();
    }
}
