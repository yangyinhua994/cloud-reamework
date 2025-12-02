package com.example.response;

import com.example.ApiException;
import com.example.enums.ResponseMessageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public static <T> Response<T> success() {
        return new Response<>(200, "成功", null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(200, "成功", data);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(200, message, data);
    }

    public static <T> Response<T> fail() {
        return new Response<>(500, "操作失败", null);
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(500, message, null);
    }

    public static <T> Response<T> fail(ResponseMessageEnum responseMessageEnum) {
        return new Response<>(500, responseMessageEnum.getMessage(), null);
    }

    public static <T> Response<T> fail(int code, String message) {
        return new Response<>(code, message, null);
    }

    public static void error(ResponseMessageEnum responseMessageEnum) {
        throw new ApiException(responseMessageEnum.getMessage());
    }

}
