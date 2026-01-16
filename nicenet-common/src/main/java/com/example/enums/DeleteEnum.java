package com.example.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum DeleteEnum {
    NOT_DELETED(0, "未删除"),
    DELETED(1, "已删除"),
    ;

    private final Integer code;
    private final String message;

    DeleteEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static boolean isDeleted(Integer deleted) {
        return Objects.equals(deleted, DELETED.getCode());
    }

    public static boolean isNotDelete(Integer deleted) {
        return Objects.equals(deleted, NOT_DELETED.getCode());
    }

}
