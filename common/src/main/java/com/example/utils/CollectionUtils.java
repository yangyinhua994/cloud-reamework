package com.example.utils;

import java.util.Collection;

public class CollectionUtils extends org.springframework.util.CollectionUtils {

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

}
