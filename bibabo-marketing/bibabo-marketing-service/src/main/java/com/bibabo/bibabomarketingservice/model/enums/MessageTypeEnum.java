package com.bibabo.bibabomarketingservice.model.enums;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 18:38
 * @Description:
 */
public enum MessageTypeEnum {
    QUEUE_ACTIVITY("marketingActivityProcessRunner", 1),
    QUEUE_GRANT_COUPONS("grantCouponProcessRunner", 2);

    MessageTypeEnum(String processName, Integer type) {
        this.processName = processName;
        this.type = type;
    }

    public static int findTypeByProcessName(String processName) {
        for (MessageTypeEnum value : MessageTypeEnum.values()) {
            if (value.getProcessName().equals(processName)) {
                return value.getType();
            }
        }
        return -1;
    }

    private String processName;

    private Integer type;

    public String getProcessName() {
        return processName;
    }

    public Integer getType() {
        return type;
    }
}
