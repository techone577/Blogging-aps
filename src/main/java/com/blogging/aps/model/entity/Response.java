package com.blogging.aps.model.entity;

/**
 * @TechoneDuan
 *
 * 统一处理返回值
 */
public class Response <T>{
    // 成功标记
    private boolean success;
    // 提示信息
    private String msg;
    // 错误码
    private int errorCode;
    // 返回的具体数据
    private T data;

    public boolean isSuccess () {
        return success;
    }

    public void setSuccess (boolean success) {
        this.success = success;
    }

    public String getMsg () {
        return msg;
    }

    public void setMsg (String msg) {
        this.msg = msg;
    }

    public int getErrorCode () {
        return errorCode;
    }

    public void setErrorCode (int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData () {
        return data;
    }

    public void setData (T data) {
        this.data = data;
    }
}
