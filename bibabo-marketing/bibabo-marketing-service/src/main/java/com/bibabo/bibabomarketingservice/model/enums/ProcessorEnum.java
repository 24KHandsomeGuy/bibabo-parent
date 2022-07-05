package com.bibabo.bibabomarketingservice.model.enums;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 14:46
 * @Description:
 */
public enum ProcessorEnum {
    ACTIVITY(0, "marketingActivityProcessRunner", "营销活动处理"),
    GRANT_COUPON(1, "grantCouponProcessRunner", "发劵通知营销资产系统处理"),
    ;

    ProcessorEnum(Integer process, String name, String desc) {
        this.process = process;
        this.name = name;
        this.desc = desc;
    }

    private Integer process;

    private String name;

    private String desc;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getProcess() {
        return process;
    }

    public static String getProcessorName(int process) {
        for (ProcessorEnum value : ProcessorEnum.values()) {
            if (value.getProcess() == process) {
                return value.getName();
            }
        }
        return null;
    }
}
