package com.bibabo.utils.model;

import java.io.Serializable;

/**
 * @author FuKuiXiang
 * @version 1.0.0
 * @date 2024/1/30 11:46
 * @Description http请求返回结果
 */
public class HttpResult<E> implements Serializable {

    private static final long serialVersionUID = 3969877420711771111L;

    /**
     * 错误码
     */
    private int code;

    /**
     * 接口调用是否成功
     */
    private boolean isSuccess;

    /**
     * 返回的错误提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private E model;

    /**
     * @return 获取错误码
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * @return 结果描述信息
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return 业务数据对象
     */
    public E getModel() {
        return model;
    }

    public void setModel(E model) {
        this.model = model;
    }

    public HttpResult<E> success(E data) {
        this.isSuccess = true;
        this.code = ReturnCode.SUCCESS.getCode();
        this.message = ReturnCode.SUCCESS.getMsg();
        this.model = data;
        return this;
    }

    public HttpResult<E> failed(ReturnCode reCode) {
        this.isSuccess = false;
        this.code = reCode.getCode();
        this.message = reCode.getMsg();
        return this;
    }

    public HttpResult<E> failed(ReturnCode reCode, String msg) {
        this.isSuccess = false;
        this.code = reCode.getCode();
        this.message = msg;
        return this;
    }

    public HttpResult<E> failed(Integer code, String msg) {
        this.isSuccess = false;
        this.code = code;
        this.message = msg;
        return this;
    }

    public HttpResult<E> failed(E date, Integer code, String msg) {
        failed(code, msg);
        this.model = date;
        return this;
    }

    public HttpResult<E> failed(E date, ReturnCode reCode, String msg) {
        this.isSuccess = false;
        this.code = reCode.getCode();
        this.message = msg;
        this.model = date;
        return this;
    }

    public HttpResult<E> success(E date, ReturnCode reCode, String msg) {
        this.isSuccess = true;
        this.code = reCode.getCode();
        this.message = msg;
        this.model = date;
        return this;
    }
}
