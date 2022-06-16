package com.bibabo.bibabowdmservice.model.enums;

/**
 * @author fukuixiang
 * @date 2022/6/15
 * @time 19:16
 * @description
 */
public enum ElasticsearchIndexEnum {
    SKU("sku", "商品索引");

    private String index;

    private String desc;

    ElasticsearchIndexEnum(String index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public String getIndex() {
        return index;
    }

    public String getDesc() {
        return desc;
    }
}
