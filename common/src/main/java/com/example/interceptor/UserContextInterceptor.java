package com.example.interceptor;

import com.example.entity.User;
import com.example.holder.UserContextHolder;
import com.example.properties.AppProperties;
import com.example.properties.SpringProperties;
import com.example.utils.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserContextInterceptor implements HandlerInterceptor {

    private final SpringProperties springProperties;
    private final AppProperties appProperties;
    private final ObjectMapper objectMapper;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头提取网关透传的用户信息
        String userId = request.getHeader(UserContextHolder.USER_ID);
        String username = request.getHeader(UserContextHolder.USERNAME);
        String userType = request.getHeader(UserContextHolder.USER_TYPE);
        if (StringUtils.isBlank(userId) && !shouldSkip(request.getRequestURI())) {
            buildUnauthorizedResponse(response);
            return false;
        }
        // 进行解码
        if (StringUtils.isNotBlank(username)) {
            try {
                username = URLDecoder.decode(username, StandardCharsets.UTF_8);
            } catch (Exception e) {
                buildUnauthorizedResponse(response);
                return false;
            }
        }
        try {
            User user = new User();
            user.setId(Long.parseLong(userId));
            user.setUsername(username);
            user.setUserType(Integer.parseInt(userType));
            UserContextHolder.setUser(user);
        } catch (Exception e) {
            log.error("解析用户信息失败, userId: {}, username: {}, userType: {}", userId, username, userType);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContextHolder.clear();
    }

    private boolean shouldSkip(String path) {
        return appProperties.getConfig().getSecurity().getIgnore().getUrls()
                .stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    private void buildUnauthorizedResponse(HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("message", "请先登录");

        try {
            String jsonStr = objectMapper.writeValueAsString(responseBody);
            response.getWriter().write(jsonStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
