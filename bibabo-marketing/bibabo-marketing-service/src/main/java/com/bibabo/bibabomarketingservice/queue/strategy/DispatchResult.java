package com.bibabo.bibabomarketingservice.queue.strategy;

import lombok.Data;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 16:08
 * @Description:
 */
@Data
public class DispatchResult {

    private int nextStatus;

    private int isFinish;
}
