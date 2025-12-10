package com.example.utils;

public class StringUtils extends com.alibaba.nacos.api.utils.StringUtils {

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
