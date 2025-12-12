package com.example.utils;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {

    public static final String REDIS_KEY_SEPARATOR = ":";

    // 默认30分钟
    public static final long DEFAULT_TIMEOUT = 30 * 60;
    @Getter
    @Setter
    private static RedisUtil instance;

    private final RedisTemplate<String, Object> objectRedisTemplate;
    private final RedisTemplate<String, byte[]> bytesRedisTemplate;

    @PostConstruct
    private void init() {
        setInstance(this);
    }

    public void setBytes(String key, byte[] value, long timeout, TimeUnit timeUnit) {
        try {
            RedisTemplate<String, byte[]> bytesRedisTemplate = new RedisTemplate<>();
            bytesRedisTemplate.setConnectionFactory(objectRedisTemplate.getConnectionFactory());
            // byte[]类型用JdkSerializationRedisSerializer（或直接用StringRedisSerializer但不转格式）
            bytesRedisTemplate.setKeySerializer(new StringRedisSerializer());
            bytesRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
            bytesRedisTemplate.afterPropertiesSet();

            if (timeout > 0) {
                bytesRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            } else {
                bytesRedisTemplate.opsForValue().set(key, value);
            }
        } catch (Exception e) {
            log.error("redis set byte[] error", e);
        }
    }

    public void setBytes(String key, byte[] value, long timeoutSeconds) {
        setBytes(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    public byte[] getBytes(String key) {
        try {
            return bytesRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("redis get byte[] error", e);
            return null;
        }
    }

    public boolean set(String key, Object value) {
        return set(key, value, DEFAULT_TIMEOUT);
    }

    public boolean set(String key, Object value, long timeout) {
        return set(key, value, timeout, TimeUnit.SECONDS);
    }

    public boolean set(String key, byte[] value, long timeoutSeconds) {
        return set(key, Arrays.toString(value), timeoutSeconds);
    }

    public boolean set(String key, Object value, long timeout, TimeUnit timeUnit) {
        try {
            if (timeout > 0) {
                objectRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception exception) {
            log.error("redis set error", exception);
        }
        return false;
    }

    public boolean set(String CacheName, Object key, Object value) {
        return set(buildKey(CacheName, key), value);
    }

    public Object get(String redisKey) {
        return objectRedisTemplate.opsForValue().get(redisKey);
    }

    public Object get(String redisKey, Object... keys) {
        return get(buildKey(redisKey, keys));
    }

    public Set<String> keys(String pattern) {
        try {
            return objectRedisTemplate.keys(pattern);
        } catch (Exception e) {
            return Collections.emptySet();
        }
    }

    public void delete(String CacheName, Object... keys) {
        delete(buildKey(CacheName, keys));
    }

    public boolean delete(String key) {
        log.info("delete redis key: {}", key);
        try {
            return objectRedisTemplate.delete(key);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteKeys(String CacheName, Object... keys) {
        return deleteKeys(buildKey(CacheName, keys));
    }

    public boolean deleteKeys(String keys) {
        log.info("delete redis keys: {}", keys);
        Set<String> values = keys(keys);
        return !values.isEmpty() && objectRedisTemplate.delete(values) > 0;
    }

    public boolean exists(String redisKey) {
        return objectRedisTemplate.hasKey(redisKey);
    }

    public String buildKey(String CacheName, Object... keys) {
        StringBuilder sb = new StringBuilder(CacheName.endsWith(REDIS_KEY_SEPARATOR) ? CacheName : CacheName + REDIS_KEY_SEPARATOR);
        for (Object k : keys) {
            sb.append(k).append(REDIS_KEY_SEPARATOR);
        }
        return sb.deleteCharAt(sb.length() - REDIS_KEY_SEPARATOR.length()).toString();
    }

}
