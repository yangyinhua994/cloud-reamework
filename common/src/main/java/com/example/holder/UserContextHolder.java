package com.example.holder;

import com.example.entity.User;
import com.example.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserContextHolder {
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String SERVER_NAME = "serverName";
    public static final String USER_TYPE = "userType";

    private static final ThreadLocal<User> USER_CONTEXT = new ThreadLocal<>();

    public static void setUser(User user) {
        USER_CONTEXT.set(user);
    }

    public static User getUser() {
        return USER_CONTEXT.get();
    }

    public static void clear() {
        USER_CONTEXT.remove();
    }

}
