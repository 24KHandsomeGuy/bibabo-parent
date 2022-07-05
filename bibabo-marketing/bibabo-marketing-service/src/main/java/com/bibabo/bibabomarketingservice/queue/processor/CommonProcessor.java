package com.bibabo.bibabomarketingservice.queue.processor;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 16:08
 * @Description:
 */
public interface CommonProcessor<P, R> {
    R process(P p);
}