package com.bibabo.bibabomarketingservice.model.enums;

/**
 * @Author: Damon Fu
 * @Date: 2022/6/28 15:29
 * @Description:
 */
public enum GiftStatusEnum {
    NEW(0, "新增"),
    ALLOW_TO_JOIN(1, "允许加入活动"),
    NOT_ALLOW_TO_JOIN(2, "不允许加入活动"),
    SUCCESS(3, "附赠成功"),
    FAILD(4, "附赠失败"),
    ;

    GiftStatusEnum(Integer status, String desc) {
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
