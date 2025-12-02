package com.example.holder;

import com.example.entity.User;

public class UserContextHolder {
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
