package com.example.enums;

import lombok.Getter;

@Getter
public enum ResponseMessageEnum {
    COMMUNITY_NAME_EXIST("小区名称已存在"),
    USER_EXIST("用户已存在"),
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
    NO_AUTHORITY("无权访问该接口"),
    REFRESH_TOKEN_ERROR("刷新token无效或已过期"),
    EMPTY_PARAMETER("存在空参数"),
    ;

    private final String message;

    ResponseMessageEnum(String message) {
        this.message = message;
    }

}
