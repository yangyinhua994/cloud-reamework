package com.example.exception;

import com.example.enums.ResponseMessageEnum;

public class ApiException extends RuntimeException {

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public static void error(ResponseMessageEnum responseMessageEnum) {
        if (responseMessageEnum == null) {
            throw new ApiException();
        }
        error(responseMessageEnum.getMessage());
    }

    public static void error(String msg) {
        throw new ApiException(msg);
    }

}
