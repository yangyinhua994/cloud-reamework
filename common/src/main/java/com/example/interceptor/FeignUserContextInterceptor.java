package com.example.interceptor;

import com.example.entity.User;
import com.example.holder.UserContextHolder;
import com.example.properties.SpringProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FeignUserContextInterceptor implements RequestInterceptor {

    private final SpringProperties springProperties;

    @Override
    public void apply(RequestTemplate template) {

        // 透传服务名
        String serverName = springProperties.getApplication().getName();
        template.header(UserContextHolder.SERVER_NAME, serverName);

        Object userId = UserContextHolder.getUserId();
        if (userId != null) {
            template.header(UserContextHolder.USER_ID, userId.toString());
        }

        Object username = UserContextHolder.getUsername();
        if (username != null) {
            String encodedUsername = URLEncoder.encode(username.toString(), StandardCharsets.UTF_8);
            template.header("username", encodedUsername);
        }

    }
}
