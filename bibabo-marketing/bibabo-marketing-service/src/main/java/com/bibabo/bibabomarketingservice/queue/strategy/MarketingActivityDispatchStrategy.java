package com.bibabo.bibabomarketingservice.queue.strategy;

import com.bibabo.bibabomarketingservice.model.enums.GiftStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 16:17
 * @Description:
 */
@Data
@AllArgsConstructor
public class MarketingActivityDispatchStrategy implements DispatchStrategy {

    private int code;

    @Override
    public DispatchResult dispatch() {
        DispatchResult result = new DispatchResult();
        if (code == 1) {
            result.setIsFinish(0);
            result.setNextStatus(GiftStatusEnum.ALLOW_TO_JOIN.getStatus());
        } else if (code == 2) {
            result.setIsFinish(1);
            result.setNextStatus(GiftStatusEnum.NOT_ALLOW_TO_JOIN.getStatus());
        } else {
            result.setIsFinish(0);
            result.setNextStatus(GiftStatusEnum.NEW.getStatus());
        }
        return result;
    }
}
