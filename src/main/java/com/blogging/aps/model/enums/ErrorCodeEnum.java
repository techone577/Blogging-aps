package com.blogging.aps.model.enums;

/**
 * @author techoneduan
 * @date 2018/12/13
 */

public enum ErrorCodeEnum {

    /**
     * BSP error
     */
    NO_SERVICE_AVAILABLR(0, "服务不可用"),

    /**
     * System error
     */
    UNKNOWN(10000, "未知异常"),
    PARAM_ILLEGAL_ERROR(10001, "参数非法"),
    ERROR_REPEAT_ACTION(10002, "重复执行"),
    SYSTEM_ERROR(10003, "系统异常"),
    INTERFACE_ERROR(10004, "接口异常"),
    DB_ERROR(10005, "数据库异常"),

    /**
     * BM error
     */
    POST_STATE_ERROR(30000, "文章状态异常"),
    TAG_NAME_ALREADY_EXIST_ERROR(30001, "标签名称已存在"),
    TAG_ALREADY_EXIST_ERROR(30002, "标签已存在"),
    TAG_NOT_EXIST_ERROR(30003, "标签不存在"),
    TITLE_EMPTY_ERROR(30004, "文章标题为空"),
    POST_NOT_EXIST_ERROR(30005, "文章不存在"),
    CATEGORY_ALREADY_EXIT(30006, "分类不存在");


    private int code;

    private String msg;

    ErrorCodeEnum(int code, String msg) {
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
