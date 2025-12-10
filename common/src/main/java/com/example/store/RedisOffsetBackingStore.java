package com.example.store;

import com.example.utils.RedisUtil;
import org.apache.kafka.connect.runtime.WorkerConfig;
import org.apache.kafka.connect.storage.OffsetBackingStore;
import org.apache.kafka.connect.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Component
public class RedisOffsetBackingStore implements OffsetBackingStore {

    private RedisUtil redisUtil = RedisUtil.getInstance();
    private String redisKey;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void configure(WorkerConfig config) {
        try {
            Map<String, Object> originals = config.originals();
            redisKey = (String) originals.get("offset.storage.redis.key");
            if (redisKey == null) {
                throw new IllegalArgumentException("Required Redis configuration missing: key");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to configure RedisOffsetBackingStore: " + e.getMessage(), e);
        }
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public Future<Map<ByteBuffer, ByteBuffer>> get(Collection<ByteBuffer> keys) {
        return CompletableFuture.supplyAsync(() -> {
            Map<ByteBuffer, ByteBuffer> result = new HashMap<>();
            for (ByteBuffer key : keys) {
                byte[] keyBytes = key.array();
                String hexKey = bytesToHex(keyBytes);
                byte[] value = redisUtil.getBytes((redisKey + ":" + hexKey));
                if (value != null) {
                    result.put(key, ByteBuffer.wrap(value));
                }
            }
            return result;
        });
    }

    @Override
    public Future<Void> set(Map<ByteBuffer, ByteBuffer> values, Callback<Void> callback) {
        return CompletableFuture.runAsync(() -> {
            try {
                for (Map.Entry<ByteBuffer, ByteBuffer> entry : values.entrySet()) {
                    byte[] keyBytes = entry.getKey().array();
                    byte[] valueBytes = entry.getValue().array();
                    String hexKey = bytesToHex(keyBytes);
                    // 需要验证 valueBytes 是否为有效的 Map 序列化数据
                    redisUtil.setBytes((redisKey + ":" + hexKey), valueBytes, 60 * 60 * 24 * 7);
                }
                callback.onCompletion(null, null);
            } catch (Exception e) {
                callback.onCompletion(e, null);
            }
        });
    }

    @Override
    public Set<Map<String, Object>> connectorPartitions(String connectorName) {
        return Set.of();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
