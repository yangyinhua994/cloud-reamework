package com.example.utils;

import com.example.entity.User;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.holder.UserContextHolder;
import com.example.properties.AppConfigSecurityJwtProperties;
import com.example.vo.UserVO;
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

    public String generate(Long userId, String username, Integer userType, Long expireMinute) {
        return generate(userId, username, userType, expireMinute, false);
    }

    public String generate(Long userId, String username, Integer userType, Long expireMinute, Boolean isRefreshToken) {
        try {
            if (ObjectUtils.isExitsEmpty(userId, username, userType, expireMinute, isRefreshToken)) {
                ApiException.error(ResponseMessageEnum.EMPTY_PARAMETER);
            }
            Map<String, String> claims = new HashMap<>();
            claims.put(UserContextHolder.USER_ID, userId.toString());
            claims.put(UserContextHolder.USERNAME, username);
            claims.put(UserContextHolder.USER_TYPE, userType.toString());
            claims.put(UserContextHolder.IS_REFRESH_TOKEN, isRefreshToken.toString());

            SecretKey key = Keys.hmacShaKeyFor(appConfigSecurityJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
            return Jwts.builder().claims(claims)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expireMinute * 60 * 1000)).signWith(key).compact();
        } catch (Exception e) {
            log.error("生成JWT token失败: {}", e.getMessage(), e);
            throw new RuntimeException("Token生成失败", e);
        }

    }

    public String generateToken(User user) {
        return generateToken(user.getId(), user.getUsername(), user.getUserType());
    }

    public String generateToken(Long userId, String username, Integer userType) {
        Long expireMinute = appConfigSecurityJwtProperties.getExpireMinute();
        return generate(userId, username, userType, expireMinute);
    }

    public String generateRefreshToken(User user) {
        return generateRefreshToken(user.getId(), user.getUsername(), user.getUserType());
    }

    public String generateRefreshToken(Long userId, String username, Integer userType) {
        Long expireMinute = appConfigSecurityJwtProperties.getRefreshExpireMinute();
        return generate(userId, username, userType, expireMinute, true);
    }

    public Claims parseToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(appConfigSecurityJwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));

            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            log.error("解析JWT token失败: {}", e.getMessage(), e);
            return null;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return true;
            }
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("验证JWT token过期失败: {}", e.getMessage(), e);
            return true;
        }
    }

    public boolean isRefreshToken(String token) {
        if (isTokenExpired(token)) {
            return false;
        }
        return getBoolean(token, UserContextHolder.IS_REFRESH_TOKEN, false);
    }

    public Object get(String token, String key) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.get(key);
    }

    public String getString(String token, String key, String defaultValue) {
        Object o = get(token, key);
        return o == null ? defaultValue : o.toString();
    }

    public Boolean getBoolean(String token, String key, Boolean defaultValue) {
        Object o = get(token, key);
        try {
            return o == null ? defaultValue : Boolean.valueOf(o.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public Long getLong(String token, String key, Long defaultValue) {
        Object o = get(token, key);
        try {
            return o == null ? defaultValue : Long.valueOf(o.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public Integer getInteger(String token, String key, Integer defaultValue) {
        Object o = get(token, key);
        try {
            return o == null ? defaultValue : Integer.valueOf(o.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public UserVO refreshToken(String refreshToken) {
        if (!isRefreshToken(refreshToken)) {
            ApiException.error(ResponseMessageEnum.REFRESH_TOKEN_ERROR);
        }
        if (isTokenExpired(refreshToken)) {
            ApiException.error(ResponseMessageEnum.REFRESH_TOKEN_ERROR);
        }
        Long userId = getLong(refreshToken, UserContextHolder.USER_ID, null);
        String username = getString(refreshToken, UserContextHolder.USERNAME, null);
        Integer userType = getInteger(refreshToken, UserContextHolder.USER_TYPE, null);
        UserVO userVO = new UserVO();
        userVO.setToken(generateToken(userId, username, userType));
        userVO.setRefreshToken(generateRefreshToken(userId, username, userType));
        return userVO;
    }
}
