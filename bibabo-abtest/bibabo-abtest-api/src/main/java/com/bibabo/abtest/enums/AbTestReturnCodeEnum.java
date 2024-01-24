package com.bibabo.abtest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/23 16:48
 * @Description A/B测试返回CODE定义
 */
@AllArgsConstructor
@Getter
public enum AbTestReturnCodeEnum {

    SUCCESS("0000", 10000, "A/B成功", null), SYSTEM_EXCEPTION("1001", 11001, "A/B系统异常", null),
    DOMAIN_ID_EXCEPTION("1002", 11002, "实验域ID不可以为空!", null), EXPERIMENT_ID_EXCEPTION("1003", 11003, "实验ID不可以为空!", null);

    private final String code;
    private final int key;
    private final String value;
    private final Object data;

    public boolean isSuccess() {
        if ("0000".equals(this.code)) {
            return true;
        }
        return false;
    }
}
