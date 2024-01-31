package com.bibabo.utils.monitor;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/22 18:39
 * @Description:
 */
public class Profiler {

    private static final TransmittableThreadLocal<Long> TIME_THREADLOCAL = new TransmittableThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
}
