package com.bibabo.utils.monitor;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/22 18:39
 * @Description:
 */
public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        long takeTime = System.currentTimeMillis() - TIME_THREADLOCAL.get();
        return takeTime;
    }
}
