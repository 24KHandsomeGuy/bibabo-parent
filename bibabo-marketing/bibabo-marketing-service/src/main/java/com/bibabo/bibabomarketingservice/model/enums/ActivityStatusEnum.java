package com.bibabo.bibabomarketingservice.model.enums;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 10:43
 * @Description:
 */
public enum ActivityStatusEnum {
    INIT(0, "新建"),
    ENABLE(1, "启用"),
    DISABLE(-1, "停用");

    ActivityStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    private Integer status;

    private String desc;

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
