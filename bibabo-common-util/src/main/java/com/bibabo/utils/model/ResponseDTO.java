/**
 *
 */
package com.bibabo.utils.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author fukuixiang
 * @date 2021/12/31
 * @time 11:12
 * @description Rpc响应传输对象
 */
public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean success;

    private String message;

    private T data;

    private List<T> dataList;

    public ResponseDTO() {
    }

    public ResponseDTO(Boolean success, String message, T data, List<T> dataList) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.dataList = dataList;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public static <T> ResponseDTO.Builder<T> builder() {
        return new ResponseDTO.Builder<T>();
    }

    /**
     * 建造器
     */
    public static class Builder<T> {

        private Boolean success;

        private String message;

        private T data;

        private List<T> dataList;

        public Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Builder<T> dataList(List<T> dataList) {
            this.dataList = dataList;
            return this;
        }

        public Builder<T> success() {
            this.success = true;
            return this;
        }

        public Builder<T> fail() {
            this.success = false;
            return this;
        }

        public Builder<T> success(T data) {
            this.success = true;
            this.data = data;
            return this;
        }

        public Builder<T> success(List<T> dataList) {
            this.success = true;
            this.dataList = dataList;
            return this;
        }

        public Builder<T> fail(String message) {
            this.success = false;
            this.message = message;
            return this;
        }

        public ResponseDTO<T> build() {
            return new ResponseDTO<T>(success, message, data, dataList);
        }
    }
}
