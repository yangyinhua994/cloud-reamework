package com.example.filter;

import com.alibaba.nacos.api.utils.StringUtils;
import com.example.properties.SpringProperties;
import com.example.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class TokenParseFilter implements GlobalFilter, Ordered {

    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String SERVER_NAME = "serverName";

    private final JwtUtil jwtUtil;
    private final SpringProperties springProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
        if (!StringUtils.isBlank(token) && jwtUtil.validateToken(token)) {
            String username = jwtUtil.get(token, USERNAME, "");
            ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
                    .header(USER_ID, jwtUtil.get(token, USER_ID, ""))
                    .header(USERNAME, StringUtils.isBlank(username) ? "" : URLEncoder.encode(username, StandardCharsets.UTF_8))
                    .header(SERVER_NAME, springProperties.getApplication().getName())
                    .build();
            return chain.filter(exchange.mutate().request(serverHttpRequest).build());
        }
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return -1;
    }
}