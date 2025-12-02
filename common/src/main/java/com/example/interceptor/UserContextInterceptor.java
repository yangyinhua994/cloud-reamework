package com.example.interceptor;

import com.alibaba.nacos.common.utils.StringUtils;
import com.example.entity.User;
import com.example.holder.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserContextInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头提取网关透传的用户信息
        String userId = request.getHeader("X-Id");
        String username = request.getHeader("X-user-Name");
        String realName = request.getHeader("X-real-Name");
        String nickname = request.getHeader("X-nick-Name");
        if (StringUtils.isBlank(userId)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        User user = new User();
        user.setId(Long.valueOf(userId));
        user.setUsername(username);
        user.setRealName(realName);
        user.setNickname(nickname);
        UserContextHolder.setUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }
}
