package com.ning.exception;

import com.ning.enums.AppHttpCodeEnum;

/**
 *
 */
public class SystemException extends BaseException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    public SystemException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }
    
}
