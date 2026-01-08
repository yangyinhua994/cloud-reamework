package com.example.utils;

import com.example.entity.User;
import com.example.holder.UserContextHolder;
import com.example.properties.AppConfigSecurityJwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final AppConfigSecurityJwtProperties appConfigSecurityJwtProperties;

    /**
     * 生成token
     *
     * @param claims 自定义claims
     * @return JWT token
     */
    public String generateToken(Map<String, String> claims) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(appConfigSecurityJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

            long expireMinute = appConfigSecurityJwtProperties.getExpireMinute();

            return Jwts.builder().claims(claims)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expireMinute * 60 * 1000)).signWith(key).compact();
        } catch (Exception e) {
            log.error("生成JWT token失败: {}", e.getMessage(), e);
            throw new RuntimeException("Token生成失败", e);
        }
    }

    public String generateToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put(UserContextHolder.USER_ID, user.getId().toString());
        claims.put(UserContextHolder.USERNAME, user.getUsername());
        return generateToken(claims);
    }

    /**
     * 解析JWT token并提取claims
     *
     * @param token JWT token
     * @return Claims对象
     */
    public Claims parseToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(appConfigSecurityJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            log.error("解析JWT token失败: {}", e.getMessage(), e);
            throw new RuntimeException("Token解析失败", e);
        }
    }

    /**
     * 从token中提取指定key的值
     *
     * @param token JWT token
     * @param key   键名
     * @return 值
     */
    public String get(String token, String key) {
        Claims claims = parseToken(token);
        return claims.get(key, String.class);
    }

}
