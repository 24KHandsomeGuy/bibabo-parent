package com.bibabo.bibaboorderservice.util.timewheel;

import org.apache.dubbo.common.timer.HashedWheelTimer;
import org.apache.dubbo.common.utils.NamedThreadFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author fukuixiang
 * @date 2022/6/13
 * @time 14:16
 * @description
 */
public class WheelTimerFactory {

    /**
     * 5s一次，守护线程，任务可丢弃
     */
    public static final HashedWheelTimer FIVE_SECONDS_WHEEL_TIMER = new HashedWheelTimer(new NamedThreadFactory("WHEEL_TIMER_THREAD", true), 5000, TimeUnit.MILLISECONDS, 1024);
}
