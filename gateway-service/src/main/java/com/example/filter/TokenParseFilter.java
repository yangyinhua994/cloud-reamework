package com.example.filter;

import com.alibaba.nacos.api.utils.StringUtils;
import com.example.properties.AppConfigSecurityIgnoreConfigProperties;
import com.example.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class TokenParseFilter implements GlobalFilter, Ordered {

    private final AppConfigSecurityIgnoreConfigProperties appConfigSecurityIgnoreConfigProperties;
    private final JwtUtil jwtUtil;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (shouldSkip(path)) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        try {
            if (!StringUtils.isBlank(token) && jwtUtil.validateToken(token)) {
                String userId = jwtUtil.getIdFromToken(token).toString();
                String username = jwtUtil.get(token, "username").toString();
                if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(username)) {
                    String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
                    ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                            .header("userId", userId)
                            .header("username", encodedUsername)
                            .build();
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                }
            }
        } catch (Exception ignored) {
        }
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }


    /**
     * 判断是否跳过token验证
     *
     * @param path 请求路径
     * @return 是否跳过
     */
    private boolean shouldSkip(String path) {
        return appConfigSecurityIgnoreConfigProperties.getUrls().stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
