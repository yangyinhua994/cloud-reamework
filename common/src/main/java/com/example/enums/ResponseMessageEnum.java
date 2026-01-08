package com.example.enums;

import lombok.Getter;

@Getter
public enum ResponseMessageEnum {
    COMMUNITY_NAME_EXIST("小区名称已存在"),
    USER_NAME_EXIST("用户名已存在"),
    ID_NOT_NULL("ID不能为空"),
    USER_NOT_EXIST("用户不存在"),
    ACCOUNT_ERROR("账号异常"),
    USERNAME_REPEAT("用户名重复"),
    ACCOUNT_OR_PASSWORD_ERROR("账号或密码错误"),
    PHONE_IS_REGISTERED("电话已注册"),
    DATA_NOT_EXIST("数据不存在"),
    ID_NOT_EXIST("ID有误"),
    PHONE_NOT_EXIST("电话不能为空"),
    SERVICE_NOT_AVAILABLE("服务不可用"),
    ;

    private final String message;

    ResponseMessageEnum(String message) {
        this.message = message;
    }

    }
