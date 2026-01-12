package com.example.response;

import com.example.exception.ApiException;
import com.example.enums.ResponseMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {

    private static final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "成功";

    private static final int FAIL_CODE = 500;
    private static final String FAIL_MESSAGE = "操作失败";

    private int code;
    private String message;
    private T data;

    public static <T> Response<T> success() {
        return new Response<>(SUCCESS_CODE, SUCCESS_MESSAGE, null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(SUCCESS_CODE, message, data);
    }

    public static <T> Response<T> fail() {
        return new Response<>(FAIL_CODE, FAIL_MESSAGE, null);
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(FAIL_CODE, message, null);
    }

    public static <T> Response<T> fail(ResponseMessageEnum responseMessageEnum) {
        return new Response<>(FAIL_CODE, responseMessageEnum.getMessage(), null);
    }

    public static <T> Response<T> fail(int code, String message) {
        return new Response<>(code, message, null);
    }

    public static void error(ResponseMessageEnum responseMessageEnum) {
        error(responseMessageEnum.getMessage());
    }

    public static void error(String message) {
        throw new ApiException(message);
    }

    @Transient
    public boolean isSuccess() {
        return code == SUCCESS_CODE;
    }

    @Transient
    public boolean isFail() {
        return code != SUCCESS_CODE;
    }

}
