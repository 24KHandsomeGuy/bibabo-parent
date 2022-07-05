package com.bibabo.bibabomarketingservice.queue.processor;

import com.bibabo.bibabomarketingservice.model.dto.ActivityDTO;
import com.bibabo.bibabomarketingservice.queue.strategy.DispatchStrategy;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 16:08
 * @Description:
 */
public interface QueueProcessor<T> extends CommonProcessor<T, DispatchStrategy> {

    DispatchStrategy process(T t);
}
