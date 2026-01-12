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

        // feign 调用时透传用户信息
        String serverName = springProperties.getApplication().getName();
        template.header(UserContextHolder.SERVER_NAME, serverName);
        User user = UserContextHolder.getUser();
        if (user == null) {
            return;
        }
        if (user.getId() != null) {
            template.header(UserContextHolder.USER_ID, user.getId().toString());
        }

        if (user.getUsername() != null) {
            String encodedUsername = URLEncoder.encode(user.getUsername(), StandardCharsets.UTF_8);
            template.header("username", encodedUsername);
        }

        if (user.getUserType() != null) {
            template.header(UserContextHolder.USER_TYPE, user.getUserType().toString());
        }

    }
}
