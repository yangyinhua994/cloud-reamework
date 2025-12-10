package com.example.interceptor;

import com.example.entity.User;
import com.example.holder.UserContextHolder;
import com.example.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;

@Component
public class UserContextInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头提取网关透传的用户信息
        String userId = request.getHeader("userId");
        String username = request.getHeader("username");
        if (StringUtils.isBlank(userId)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        // 对username进行URL解码
        if (StringUtils.isNotBlank(username)) {
            try {
                username = java.net.URLDecoder.decode(username, StandardCharsets.UTF_8);
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
        User user = new User();
        user.setId(Long.valueOf(userId));
        user.setUsername(username);
        UserContextHolder.setUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }
}
