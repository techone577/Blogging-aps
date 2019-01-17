package com.blogging.aps.model.enums;

/**
 * @author techoneduan
 * @date 2018/12/13
 */

public enum ErrorCodeEnum {
    //通用错误码 10950001-10950099

    NO_SERVICE_AVAILABLR(0, "服务不可用"),;

    private int code;

    private String msg;

    ErrorCodeEnum (int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
