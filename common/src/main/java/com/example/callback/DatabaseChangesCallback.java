package com.example.callback;

import com.example.entity.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
public abstract class DatabaseChangesCallback<T extends BaseEntity> {

    private final Map<Object, Set<Object>> REDIS_KEY_MAP = new HashMap<>();

    public void onInsert(T beforeEntity, T afterEntity) {
    }

    public void onUpdate(T beforeEntity, T afterEntity) {
    }

    public void onDelete(T beforeEntity, T afterEntity) {
    }

    public void onChanged(T entity) {
    }

    public void onChange() {
    }

    public void removeCallback() {
    }

    public void addRedisKey(Collection<Object> keys, Object value) {
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        keys.forEach(k -> addRedisKey(k, value));
    }

    public synchronized void addRedisKey(Object key, Object value) {
        log.debug("添加redis key， key = {}, value = {}", key, value);
        Set<Object> set = REDIS_KEY_MAP.computeIfAbsent(key, k -> new HashSet<>());
        set.add(value);
    }

    public synchronized Set<Object> getRedisKeys(Object key) {
        return REDIS_KEY_MAP.get(key);
    }

    public boolean isChanged(T beforeEntity, T afterEntity) {
        return !Objects.equals(beforeEntity, afterEntity);
    }

    public abstract void addCallback();

    public String getTableName() {
        return "";
    }

    public List<String> getTableNames() {
        return List.of();
    }

    public Class<T> getEntityClass() {
        return null;
    }


}
