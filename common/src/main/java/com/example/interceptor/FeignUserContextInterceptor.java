package com.example.interceptor;

import com.example.entity.User;
import com.example.holder.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class FeignUserContextInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        User user = UserContextHolder.getUser();
        if (user != null) {
            template.header("userId", String.valueOf(user.getId()));
            if (user.getUsername() != null) {
                String encodedUsername = URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8);
                template.header("username", encodedUsername);
            }
        }
    }
}
