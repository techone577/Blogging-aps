package com.blogging.aps.support.exception;


import com.blogging.aps.model.enums.ErrorCodeEnum;

/**
 * @author techoneduan
 * @date 2018/12/13
 *
 * 异常统一处理入口
 */
public class UnifiedException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private ErrorCodeEnum errorCodeEnum;

    public UnifiedException(String description) {
        super(description);
    }

    public UnifiedException(ErrorCodeEnum errorCodeEnum) {
        // super(errorCodeEnum.getMsg());
        this.errorCodeEnum = errorCodeEnum;
    }

    public UnifiedException(ErrorCodeEnum errorCodeEnum, String msg) {
        super(msg);
        this.errorCodeEnum = errorCodeEnum;
    }

    public UnifiedException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        super(errorCodeEnum.getMsg(), cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public UnifiedException(ErrorCodeEnum errorCodeEnum, String msg, Throwable cause){
        super(msg, cause);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }

    public void setErrorCodeEnum(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }
}
