package com.example.utils;

import com.example.exception.ApiException;
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
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class RedisUtil {

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

    public boolean setBytes(String key, byte[] value, long timeout, TimeUnit timeUnit) {
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
            return true;
        } catch (Exception e) {
            log.error("redis set byte[] error", e);
            return false;
        }
    }

    public boolean setBytes(String key, byte[] value, long timeoutSeconds) {
        return setBytes(key, value, timeoutSeconds, TimeUnit.SECONDS);
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

    public boolean set(String key, Long id, Object value) {
        return set(buildKey(key, id), value);
    }

    public String getString(String key) {
        if (key == null) {
            return null;
        }
        Object o = objectRedisTemplate.opsForValue().get(key);
        return o == null ? null : o.toString();
    }

    public Object get(String redisKey) {
        return objectRedisTemplate.opsForValue().get(redisKey);
    }

    public Object get(String redisKey, Long id) {
        return get(buildKey(redisKey, id));
    }

    public long deleteByPattern(String pattern) {
        try {
            Set<String> keys = objectRedisTemplate.keys(pattern);
            log.debug("查询keys: {}", pattern);
            log.debug("查询出条数为: {}", keys.size());
            if (!keys.isEmpty()) {
                log.debug("删除数据");
                return objectRedisTemplate.delete(keys);
            }
            return 0L;
        } catch (Exception e) {
            return 0L;
        }
    }

    public void deleteByPattern(List<String> patterns) {
        patterns.forEach(this::deleteByPattern);
    }

    /**
     * 获取匹配模式的key列表
     *
     * @param pattern 匹配模式，如 "user:*" 或 "task:*"
     * @return 匹配的key集合
     */
    public Set<String> keys(String pattern) {
        try {
            return objectRedisTemplate.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    /**
     * 删除指定键
     *
     * @param key 键
     * @return 是否删除成功
     */
    public boolean delete(String key) {
        try {
            System.out.println("删除key, " + key);
            System.out.println(getString(key));
            Boolean success = objectRedisTemplate.delete(key);
            System.out.println(success);
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long delete(Collection<Object> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return 0L;
        }
        List<String> list = keys.stream().map(Object::toString).toList();
        try {
            log.debug("删除keys, {}", list);
            return objectRedisTemplate.delete(list);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 删除指定键
     *
     * @param key 键
     * @return 是否删除成功
     */
    public boolean delete(String key, Long id) {
        return delete(buildKey(key, id));
    }

    public boolean exists(String redisKey) {
        return objectRedisTemplate.hasKey(redisKey);
    }

    public String buildKey(String key, Long id) {
        return key + id;
    }

    public String buildKey(String CacheName, List<Object> keys, List<Object> values) {
        if (StringUtils.isBlank(CacheName)) {
            return "";
        }
        if (CollectionUtils.isEmpty(keys) || CollectionUtils.isEmpty(values)) {
            return CacheName;
        }
        if (keys.size() != values.size()) {
            throw new ApiException("构建的keys和values长度不一致");
        }
        StringBuilder redisKey = new StringBuilder();
        redisKey.append(CacheName).append("::");
        for (int i = 0; i < keys.size(); i++) {
            Object key = keys.get(i);
            Object value = values.get(i);
            redisKey.append(key == null ? "" : key).append(":").append(value == null ? "" : value).append(":");
        }
        return redisKey.substring(0, redisKey.length() - 1);
    }

    public String buildKey(String CacheName, List<Object> values) {
        String value = values.stream().map(Object::toString).collect(Collectors.joining(":"));
        return CacheName + "::" + value;
    }

}
