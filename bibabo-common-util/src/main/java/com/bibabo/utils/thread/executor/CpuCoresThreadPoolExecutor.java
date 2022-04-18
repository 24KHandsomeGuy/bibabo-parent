package com.bibabo.utils.thread.executor;

import com.bibabo.utils.thread.threadfactory.CustomizableThreadFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fukuixiang
 * @date 2022/4/13
 * @time 10:33
 * @description
 */
public class CpuCoresThreadPoolExecutor extends ThreadPoolExecutor {

    private static final int CPU_CORES = Runtime.getRuntime().availableProcessors();

    public CpuCoresThreadPoolExecutor() {
        super(CPU_CORES, CPU_CORES, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new CustomizableThreadFactory("bibabo"), new CallerRunsPolicy());
    }

    public CpuCoresThreadPoolExecutor(long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(CPU_CORES, CPU_CORES, keepAliveTime, unit, workQueue);
    }

    public CpuCoresThreadPoolExecutor(long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(CPU_CORES, CPU_CORES, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CpuCoresThreadPoolExecutor(long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(CPU_CORES, CPU_CORES, keepAliveTime, unit, workQueue, handler);
    }

    public CpuCoresThreadPoolExecutor(long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(CPU_CORES, CPU_CORES, keepAliveTime, unit, workQueue, threadFactory, handler);
    }
}
