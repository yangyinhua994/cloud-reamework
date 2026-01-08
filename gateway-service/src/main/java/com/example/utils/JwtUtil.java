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

    public Claims parseToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(appConfigSecurityJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
            return Jwts.parser()
                    .verifyWith(key)
                    .clockSkewSeconds(60)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    public Object get(String token, String key) {
        Claims claims = parseToken(token);
        return claims.get(key);
    }

    public String getString(String token, String key, String defaultValue) {
        Object o = get(token, key);
        return o == null ? defaultValue : o.toString();
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

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration == null) {
            return true;
        }
        Date now = new Date(System.currentTimeMillis() - 60000);
        return expiration.before(now);
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
     * @param token          JWT token
     * @param claimsResolver claim解析器
     * @param <T>            返回类型
     * @return claim值
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claimsResolver.apply(claims);
    }
}
