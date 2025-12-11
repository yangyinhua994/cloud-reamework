package com.example.holder;

import java.util.HashMap;
import java.util.Map;

public class UserContextHolder {

    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String SERVER_NAME = "serverName";

    private static final ThreadLocal<Map<String, Object>> USER_CONTEXT = new ThreadLocal<>();

    private static void set(String key, String value) {
        Map<String, Object> map = USER_CONTEXT.get();
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(key, value);
    }

    private static String get(String key) {
        Map<String, Object> map = USER_CONTEXT.get();
        if (map == null) {
            return "";
        }
        Object o = map.get(key);
        return o == null ? null : o.toString();
    }

    public static void setUserId(String userId) {
        set(USER_ID, userId);
    }

    public static void setUsername(String username) {
        set(USERNAME, username);
    }

    public static void setServerName(String serverName) {
        set(SERVER_NAME, serverName);
    }

    public static String getUserId() {
        return get(USER_ID);
    }

    public static String getUsername() {
        return get(USERNAME);
    }

    public static String getServerName() {
        return get(SERVER_NAME);
    }

    public static void clear() {
        USER_CONTEXT.remove();
    }

}
