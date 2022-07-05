package com.bibabo.bibabomarketingservice.queue.strategy;

import com.bibabo.bibabomarketingservice.model.enums.GiftStatusEnum;
import com.bibabo.bibabomarketingservice.model.enums.ProcessorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Damon Fu
 * @Date: 2022/7/5 10:04
 * @Description:
 */
@AllArgsConstructor
@Data
public class GrantCouponDispatchStrategy implements DispatchStrategy {

    private int code;

    @Override
    public DispatchResult dispatch() {
        DispatchResult result = new DispatchResult();
        if (code == 1) {
            result.setIsFinish(1);
            result.setNextStatus(GiftStatusEnum.SUCCESS.getStatus());
        } else if (code == 2) {
            result.setIsFinish(1);
            result.setNextStatus(GiftStatusEnum.FAILD.getStatus());
        } else {
            result.setIsFinish(0);
            result.setNextStatus(GiftStatusEnum.ALLOW_TO_JOIN.getStatus());
        }
        return result;
    }
}
