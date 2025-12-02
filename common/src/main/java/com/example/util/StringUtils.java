package com.example.util;

public class StringUtils extends com.alibaba.nacos.api.utils.StringUtils {

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
