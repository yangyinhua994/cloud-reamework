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

    /**
     * 生成刷新token（较长有效期）
     *
     * @param claims 自定义claims
     * @return 刷新token
     */
    public String generateRefreshToken(Map<String, String> claims) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(appConfigSecurityJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

            // 刷新token通常有更长的有效期，例如7天
            long refreshExpireMinute = appConfigSecurityJwtProperties.getRefreshExpireMinute(); // 需要在配置中添加此属性

            return Jwts.builder().claims(claims)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + refreshExpireMinute * 60 * 1000)).signWith(key).compact();
        } catch (Exception e) {
            log.error("生成刷新JWT token失败: {}", e.getMessage(), e);
            throw new RuntimeException("刷新Token生成失败", e);
        }
    }

    public String generateToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put(UserContextHolder.USER_ID, user.getId().toString());
        claims.put(UserContextHolder.USERNAME, user.getUsername());
        return generateToken(claims);
    }

    /**
     * 生成用户刷新token
     *
     * @param user 用户信息
     * @return 刷新token
     */
    public String generateRefreshToken(User user) {
        Map<String, String> claims = new HashMap<>();
        claims.put(UserContextHolder.USER_ID, user.getId().toString());
        claims.put(UserContextHolder.USERNAME, user.getUsername());
        return generateRefreshToken(claims);
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
     * 验证token是否过期
     *
     * @param token JWT token
     * @return 是否过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("验证JWT token过期失败: {}", e.getMessage(), e);
            return true;
        }
    }

    /**
     * 刷新token
     *
     * @param refreshToken 刷新token
     * @return 新的访问token
     */
    public String refreshToken(String refreshToken) {
        if (isTokenExpired(refreshToken)) {
            throw new RuntimeException("刷新token已过期");
        }

        Claims claims = parseToken(refreshToken);
        Map<String, String> newClaims = new HashMap<>();
        newClaims.put(UserContextHolder.USER_ID, claims.get(UserContextHolder.USER_ID, String.class));
        newClaims.put(UserContextHolder.USERNAME, claims.get(UserContextHolder.USERNAME, String.class));

        return generateToken(newClaims);
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
