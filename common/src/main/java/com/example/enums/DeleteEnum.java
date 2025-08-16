package com.example.enums;

import lombok.Getter;

@Getter
public enum DeleteEnum {
    DELETED(1, "已删除"), NOT_DELETED(0, "未删除");

    private final Integer code;
    private final String message;

    DeleteEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
