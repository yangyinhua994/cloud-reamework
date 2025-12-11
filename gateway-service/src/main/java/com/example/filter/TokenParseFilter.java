package com.example.filter;

import com.alibaba.nacos.api.utils.StringUtils;
import com.example.properties.AppConfigSecurityIgnoreConfigProperties;
import com.example.properties.SpringProperties;
import com.example.utils.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenParseFilter implements GlobalFilter, Ordered {

    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String SERVER_NAME = "serverName";


    private final AppConfigSecurityIgnoreConfigProperties appConfigSecurityIgnoreConfigProperties;
    private final JwtUtil jwtUtil;
    // 注入Jackson的ObjectMapper（SpringBoot默认会自动配置）
    private final ObjectMapper objectMapper;
    private final SpringProperties springProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (shouldSkip(path)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
        try {
            if (!StringUtils.isBlank(token) && jwtUtil.validateToken(token)) {
                String userId = jwtUtil.getIdFromToken(token).toString();
                String username = jwtUtil.get(token, USERNAME).toString();
                if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(username)) {
                    String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header(USER_ID, userId)
                            .header(USERNAME, encodedUsername)
                            .header(SERVER_NAME, springProperties.getApplication().getName())
                            .build();
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                }
            }
        } catch (Exception ignored) {
        }

        // 构建未授权的JSON响应
        return buildUnauthorizedResponse(exchange.getResponse());
    }

    /**
     * 构建未授权的JSON响应
     *
     * @param response 响应对象
     * @return Mono<Void>
     */
    private Mono<Void> buildUnauthorizedResponse(ServerHttpResponse response) {
        // 设置响应状态码
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        // 设置响应头
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().add("Access-Control-Expose-Headers", "message");
        response.getHeaders().add("message", "请先登录");

        // 构建响应体的JSON数据
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", HttpStatus.UNAUTHORIZED.value());
        responseBody.put("message", "请先登录");
        responseBody.put("success", false);

        try {
            // 将Map转换为JSON字符串
            String jsonStr = objectMapper.writeValueAsString(responseBody);
            // 将JSON字符串转换为DataBuffer
            DataBuffer buffer = response.bufferFactory().wrap(jsonStr.getBytes(StandardCharsets.UTF_8));
            // 写入响应体并完成响应
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            // 异常时返回简单的JSON提示
            String errorJson = "{\"code\":401,\"message\":\"认证失败\",\"success\":false}";
            DataBuffer buffer = response.bufferFactory().wrap(errorJson.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
    }

    /**
     * 判断是否跳过token验证
     *
     * @param path 请求路径
     * @return 是否跳过
     */
    private boolean shouldSkip(String path) {
        return appConfigSecurityIgnoreConfigProperties.getUrls().stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}