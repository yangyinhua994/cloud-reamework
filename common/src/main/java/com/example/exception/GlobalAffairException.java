package com.example.exception;

import com.example.enums.ResponseMessageEnum;

public class GlobalAffairException extends RuntimeException {
    public GlobalAffairException(String message) {
        super(message);
    }

    public GlobalAffairException(ResponseMessageEnum responseMessageEnum) {
        super(responseMessageEnum == null ? null : responseMessageEnum.getMessage());
    }
}
