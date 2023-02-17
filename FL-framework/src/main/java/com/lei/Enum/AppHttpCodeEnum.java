package com.lei.Enum;

public enum AppHttpCodeEnum {

    SUCCESS(200,"操作成功!"),
    NEED_LOGIN(401,"请登录后操作!"),
    NO_OPERATOR_AUTH(403,"无权操作!"),
    SYSTEM_ERROR(500,"系统错误!"),
    USERNAME_EXIST(501,"用户名已存在!"),
    PHONENUMBER_EXIST(502,"手机号已存在!"),
    EMAIL_EXIST(503,"邮箱已存在!"),
    REQUEST_USERNAME(504,"必填写用户名!"),
    LOGIN_ERROR(505,"用户名密码错误!"),
    CONTENT_NOT_NULL(506,"内容不能为空！"),
    ARTICLE_ID_NOT_NULL(507,"文章ID不能为空！"),
    UPLOAD_TYPE_ERROR(508,"上传文件类型不正确！"),
    ID_ERROR(510,"请输入正确的id！"),
    NICKNAME_EXIST(509,"昵称已存在!"),
    INSERT_ERROR(511,"插入数据失败!"),
    TYPE_ARTICLE_NOT_NULL(512,"插入数据失败!");


    Integer code;
    String msg;

    AppHttpCodeEnum(Integer code,String errorMessage) {
        this.code = code;
        this.msg  = errorMessage;
    }

    public Integer getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
}