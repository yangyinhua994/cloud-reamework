package com.example.enums;

import lombok.Getter;

@Getter
public enum ResponseMessageEnum {
    COMMUNITY_NAME_EXIST("小区名称已存在"),
    USER_NAME_EXIST("用户名已存在"),
    ID_NOT_NULL("ID不能为空"),
    USER_NOT_EXIST("用户不存在"),
    PHONE_OR_PASSWORD_ERROR("电话或密码错误"),
    PHONE_IS_EXIST("电话已注册"),
    DATA_NOT_EXIST("数据不存在"),
    ID_NOT_EXIST("ID有误");

    private final String message;

    ResponseMessageEnum(String message) {
        this.message = message;
    }

}
