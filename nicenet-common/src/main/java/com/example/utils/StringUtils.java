package com.example.utils;

public class StringUtils extends com.alibaba.nacos.api.utils.StringUtils {

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isExistsBlank(String... str) {
        if (str == null) {
            return true;
        }
        for (String s : str) {
            if (isBlank(s)) {
                return true;
            }
        }
        return false;
    }


    public static boolean isNotExistsBlank(String... str) {
        if (str == null) {
            return false;
        }
        for (String s : str) {
            if (isBlank(s)) {
                return false;
            }
        }
        return true;
    }

}
