package com.bibabo.utils.model;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 11:48
 * @Description http请求返回码
 */
public enum ReturnCode {

    /**
     * code=10000<br>msg=成功
     */
    SUCCESS(10000, "成功"),
    /**
     * code=10001<br>msg=失败
     */
    FAILED(10001, "失败"),
    /**
     * code=10002<br>msg=异常
     */
    EXCEPTION(10002, "异常"),
    /**
     * code=10003<br>msg=参数错误
     */
    PARAMS(10003, "参数错误"),
    /**
     * code=10003<br>msg=参数错误
     */
    CONFIG_EXIST(10004, "配置重复"),
    /**
     * code=10005<br>msg=该活动下未查询到已发布子活动
     */
    NO_PUBLISH(10005, "该活动下未查询到已发布子活动！"),
    ACTIVITY_NOT_START(1001, "活动尚未开始，稍后再来看看~"),
    ACTIVITY_END(1004, "来晚啦，活动已经结束了"),
    ACTIVITY_NOT_EXITS(1006, "抱歉，出错了～"),
    ACTIVITY_NOT_FENCE(1007, "不在活动范围内"),
    /**
     * code=20001<br>msg=创建领域对象异常
     */
    CREATE_DOMAIN_OBJECT_EXCEPTION(20001, "创建领域对象异常"),
    /**
     * code=30000<br>msg=用户未登录
     */
    NO_LOGIN(30000, "用户未登录"),
    /**
     * code=30001<br>msg=未获取到页面装修数据
     */
    NO_ENTITY(30001, "未获取到页面装修数据"),
    ;

    private Integer code;
    private String msg;

    ReturnCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static ReturnCode getByCode(Integer code) {
        if (code == null) return null;
        for (ReturnCode returnCode : ReturnCode.values()) {
            if (returnCode.code.equals(code)) {
                return returnCode;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
