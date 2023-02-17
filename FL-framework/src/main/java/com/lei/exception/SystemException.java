package com.lei.exception;

import com.lei.Enum.AppHttpCodeEnum;

/**
 * @Author : 是大大大橙子噢
 * @Date : 2022/6/6 16:13
 * @Version : 1.0.0
 */


public class SystemException extends RuntimeException {

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
}
