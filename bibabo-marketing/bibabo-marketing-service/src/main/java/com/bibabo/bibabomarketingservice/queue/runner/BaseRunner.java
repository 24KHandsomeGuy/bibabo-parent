package com.bibabo.bibabomarketingservice.queue.runner;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 15:10
 * @Description:
 */
@Slf4j
public abstract class BaseRunner<T> implements Runnable {

    protected CountDownLatch doneSingal;
    protected int threadDelay;
    protected int maxRetryNum;
    protected T element;
    protected String queueName;

    public void run() {
        try {
            doWork();
            Thread.sleep(threadDelay);
        } catch (Exception e) {
            log.error("Runner执行异常", e);
        } finally {
            doneSingal.countDown();
        }
    }

    public abstract void doWork();

    public CountDownLatch getDoneSingal() {
        return doneSingal;
    }

    public void setDoneSingal(CountDownLatch doneSingal) {
        this.doneSingal = doneSingal;
    }

    public int getThreadDelay() {
        return threadDelay;
    }

    public void setThreadDelay(int threadDelay) {
        this.threadDelay = threadDelay;
    }

    public int getMaxRetryNum() {
        return maxRetryNum;
    }

    public void setMaxRetryNum(int maxRetryNum) {
        this.maxRetryNum = maxRetryNum;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
