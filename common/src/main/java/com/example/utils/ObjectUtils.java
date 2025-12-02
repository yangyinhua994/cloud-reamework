package com.example.utils;

public class ObjectUtils extends org.springframework.util.ObjectUtils {

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

}
