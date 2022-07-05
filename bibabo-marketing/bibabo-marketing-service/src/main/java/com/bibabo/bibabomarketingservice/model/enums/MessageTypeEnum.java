package com.bibabo.bibabomarketingservice.model.enums;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/27 18:38
 * @Description:
 */
public enum MessageTypeEnum {
    QUEUE_ACTIVITY(ProcessorEnum.ACTIVITY.getName(), 1),
    QUEUE_GRANT_COUPONS(ProcessorEnum.GRANT_COUPON.getName(), 2);

    MessageTypeEnum(String processName, Integer type) {
        this.processName = processName;
        this.type = type;
    }

    public static String findProcessNameByType(int type) {
        for (MessageTypeEnum value : MessageTypeEnum.values()) {
            if (value.getType() == type) {
                return value.getProcessName();
            }
        }
        return null;
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
