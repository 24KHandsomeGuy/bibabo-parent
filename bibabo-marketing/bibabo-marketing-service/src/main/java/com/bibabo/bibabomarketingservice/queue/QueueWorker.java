package com.bibabo.bibabomarketingservice.queue;

import com.bibabo.bibabomarketingservice.common.SpringContext;
import com.bibabo.bibabomarketingservice.message.sender.MessageSenderService;
import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.model.enums.MessageTypeEnum;
import com.bibabo.bibabomarketingservice.model.enums.RedisRateLimiterEnum;
import com.bibabo.bibabomarketingservice.queue.runner.BaseRunner;
import com.bibabo.utils.thread.executor.CpuCoresThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 15:10
 * @Description:
 */
@Slf4j
public class QueueWorker {

    private static int totalServiceCount = 2;

    private String processorName;

    private RedisRateLimiterEnum redisRateLimiterEnum;

    private ThreadPoolExecutor threadPoolExecutor;

    private Thread workerThread;

    @Autowired
    private QueueManager<ActivityDTO> queueManager;

    @Autowired
    private MessageSenderService messageSenderService;

    @Autowired
    private RedissonClient redissonClient;


    public void drainToRocketMq() {
        // 把queue中剩余的元素作为消息发送到rocketmq中。tag作为类型区分是一层队列缓冲"活动决策"是否通过、还是二层队列缓冲"营销资产系统"承载能力
        LinkedList<ActivityDTO> elementList = new LinkedList<>();
        int drainCount = queueManager.getBlockingQueue(processorName).drainTo(elementList);
        log.info("processorName:{} discover spring container is closed, will drain queue element to rocketMq, element size:{}", processorName, drainCount);
        if (elementList.size() > 0) {
            elementList.parallelStream().forEach(dto -> {
                dto.setType(MessageTypeEnum.findTypeByProcessName(processorName));
                messageSenderService.sendMarketingMsg(dto);
            });
        }
    }

    private void start() {
        threadPoolExecutor = new CpuCoresThreadPoolExecutor();
        workerThread = new Thread(new FetchTaskThread(), "Thread-" + processorName);
        workerThread.start();
    }

    private void stop() {
        threadPoolExecutor.shutdown();
        workerThread.interrupt();
    }

    private class FetchTaskThread implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (SpringContext.isSpringClosedSignal()) {
                    break;
                }
                try {
                    // 如果容器没有关闭
                    // 通过存活的机器数量计算出每台服务应当抓取的限流桶数量
                    int localRateLimter = (int) (redisRateLimiterEnum.getRate() / totalServiceCount);
                    // 尝试获取限流到本地
                    RRateLimiter rRateLimiter = redissonClient.getRateLimiter(redisRateLimiterEnum.getResource());
                    boolean aquireFlag = rRateLimiter.tryAcquire(localRateLimter, 1000, TimeUnit.MICROSECONDS);
                    if (!aquireFlag) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                    // 消耗本地的限流桶，如果queue没有可消费元素则阻塞
                    CountDownLatch countDownLatch = new CountDownLatch(localRateLimter);
                    while (localRateLimter > 0) {
                        BlockingQueue<ActivityDTO> blockingQueue = queueManager.getBlockingQueue(processorName);
                        ActivityDTO element = blockingQueue.take();
                        BaseRunner<ActivityDTO> process = SpringContext.getBean(processorName, BaseRunner.class);
                        process.setDoneSingal(countDownLatch);
                        process.setElement(element);
                        threadPoolExecutor.execute(process);
                        localRateLimter--;
                    }
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    log.error("worker线程:{}，已执行结束。异常：{}", Thread.currentThread().getName(), e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            }
            log.info("worker线程:{}，已执行结束", Thread.currentThread().getName());
        }
    }

    public QueueWorker(String processorName, RedisRateLimiterEnum redisRateLimiterEnum) {
        this.processorName = processorName;
        this.redisRateLimiterEnum = redisRateLimiterEnum;
    }

    public String getProcessorName() {
        return processorName;
    }
}
