package com.bibabo.bibaboorderservice.model.enums;

/**
 * @author fukuixiang
 * @date 2022/4/13
 * @time 14:47
 * @description
 */
public enum SkuTypeEnum {

    NORMAL(1, "普通商品"),
    VIRTUAL_SUIT(1, "虚拟组套"),
    NORMAL_SUIT_CHILD(1, "虚拟组套子商品"),
    ;

    SkuTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    private int type;

    private String desc;

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
