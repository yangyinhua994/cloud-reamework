package com.example;

public class ApiException extends RuntimeException{
    public ApiException(String message) {
        super(message);
    }
}
