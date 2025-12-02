package com.example.utils;

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
import java.util.function.Function;

/**
 * JWT工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final AppConfigSecurityJwtProperties appConfigSecurityJwtProperties;

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
     * 从token中提取用户ID
     *
     * @param token JWT token
     * @return 用户ID
     */
    public Object getIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("id");
    }


    /**
     * 从token中提取指定key的值
     *
     * @param token JWT token
     * @param key 键名
     * @return 值
     */
    public Object get(String token, String key) {
        Claims claims = parseToken(token);
        return claims.get(key);
    }


    /**
     * 验证token是否有效
     *
     * @param token JWT token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断token是否过期
     *
     * @param token JWT token
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 从token中提取过期时间
     *
     * @param token JWT token
     * @return 过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 从token中提取指定claim
     *
     * @param token JWT token
     * @param claimsResolver claim解析器
     * @param <T> 返回类型
     * @return claim值
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }
}
