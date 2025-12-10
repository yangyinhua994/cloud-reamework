package com.example.broadcast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.example.callback.DatabaseChangesCallback;
import com.example.dto.DatabaseBaseDTO;
import com.example.entity.BaseEntity;
import com.example.properties.DebeziumProperties;
import com.example.store.FileDatabaseHistoryStore;
import com.example.store.RedisOffsetBackingStore;
import com.example.utils.ObjectUtils;
import com.example.utils.RedisUtil;
import com.example.utils.StringUtils;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
@Component
@Slf4j
public class DatabaseChangesBroadcast<T extends BaseEntity> {

    private final DebeziumProperties debeziumProperties;
    /**
     * 这个一定得保留，因为存储偏移量的RedisOffsetBackingStore是通过反射实例化的，而RedisOffsetBackingStore中redisUtil是通过静态方法getInstance,
     * 所以的保证RedisUtil的实例化需要在RedisOffsetBackingStore初始化之前，所以在这里注入redisUtil达到先初始化redisUtil的效果
     */
    private final RedisUtil redisUtil;

    private final Map<String, Set<DatabaseChangesCallback<T>>> callbacks = new HashMap<>();

    @PostConstruct
    public void start() {
        Properties props = new Properties();
        setBaseProps(props);
        setDatabaseProperties(props);
        setRedisStorageProperties(props);
        setFileStorageProperties(props);
        DebeziumEngine<ChangeEvent<String, String>> engine = DebeziumEngine.create(Json.class)
                .using(props)
                .notifying(record -> receiveChangeEvent(record.value()))
                .build();
        Executors.newSingleThreadExecutor().execute(engine);
    }

    public void addCallback(DatabaseChangesCallback<T> callback) {
        addCallback(callback.getTableName(), callback);
        List<String> tableNames = callback.getTableNames();
        if (CollectionUtils.isNotEmpty(tableNames)) {
            for (String tableName : tableNames) {
                addCallback(tableName, callback);
            }
        }

    }

    public void addCallback(String tableName, DatabaseChangesCallback<T> callback) {
        if (StringUtils.isNotBlank(tableName)) {
            Set<DatabaseChangesCallback<T>> callbacks = this.callbacks.computeIfAbsent(tableName, k -> new HashSet<>());
            callbacks.add(callback);
        }
    }

    public void removeCallback(DatabaseChangesCallback<T> callback) {
        removeCallback(callback.getTableName(), callback);
    }

    public void removeCallback(String tableName, DatabaseChangesCallback<T> callback) {
        if (StringUtils.isNotBlank(tableName)) {
            Set<DatabaseChangesCallback<T>> callbacks = this.callbacks.get(tableName);
            if (callbacks != null) {
                callbacks.remove(callback);
            }
        }

    }

    public void setBaseProps(Properties props) {
        if (props == null) {
            throw new RuntimeException("props is null");
        }
        props.setProperty("name", debeziumProperties.getDatasourceDatabase() + ".*");
        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("database.server.id", String.valueOf(Math.abs(UUID.randomUUID().hashCode())));
        props.setProperty("database.server.name", debeziumProperties.getServiceName());
        props.setProperty("database.include.list", debeziumProperties.getDatasourceDatabase());
        props.setProperty("table.include.list", debeziumProperties.getDatasourceDatabase() + ".*");
        props.setProperty("snapshot.mode", "initial");
        props.setProperty("poll.interval.ms", "60000");
        props.setProperty("max.queue.size", "8192");
        props.setProperty("max.batch.size", "2048");
        props.setProperty("database.history.skip.unparseable.ddl", "true");
        props.setProperty("inconsistent.schema.handling.mode", "warn");
        props.setProperty("snapshot.locking.mode", "none");
    }

    public void setDatabaseProperties(Properties props) {
        if (props == null) {
            throw new RuntimeException("props is null");
        }
        String datasourceHost = debeziumProperties.getDatasourceAddr();
        Integer datasourcePort = debeziumProperties.getDatasourcePort();
        String datasourceUserName = debeziumProperties.getDatasourceUserName();
        String datasourcePassword = debeziumProperties.getDatasourcePassword();
        if (datasourceHost != null) {
            props.setProperty("database.hostname", datasourceHost);
        }
        if (datasourcePort != null) {
            props.setProperty("database.datasourcePort", String.valueOf(datasourcePort));
        }
        if (datasourceUserName != null) {
            props.setProperty("database.user", datasourceUserName);
        }
        if (datasourcePassword != null) {
            props.setProperty("database.password", datasourcePassword);
        }
    }

    public void setRedisStorageProperties(Properties props) {
        if (props == null) {
            throw new RuntimeException("props is null");
        }
        String redisHost = debeziumProperties.getRedisHost();
        Integer redisPort = debeziumProperties.getRedisPort();
        String redisUsername = debeziumProperties.getRedisUsername();
        String redisPassword = debeziumProperties.getRedisPassword();
        Integer redisDatabase = debeziumProperties.getRedisDatabase();
        props.setProperty("offset.storage", RedisOffsetBackingStore.class.getName());
        props.setProperty("offset.storage.redis.host", redisHost);
        props.setProperty("offset.storage.redis.key", "debezium:offsets:" + debeziumProperties.getServiceName());
        if (ObjectUtils.isNotEmpty(redisPort)) {
            props.setProperty("offset.storage.redis.port", String.valueOf(redisPort));
        }
        if (StringUtils.isNotBlank(redisUsername)) {
            props.setProperty("offset.storage.redis.username", redisUsername);
        }
        if (StringUtils.isNotBlank(redisPassword)) {
            props.setProperty("offset.storage.redis.password", redisPassword);
        }
        if (ObjectUtils.isNotEmpty(redisDatabase) && redisDatabase != 0) {
            props.setProperty("offset.storage.redis.useDatabase", "true");
            props.setProperty("offset.storage.redis.database", String.valueOf(redisDatabase));
        } else {
            props.setProperty("offset.storage.redis.useDatabase", "false");
        }
        props.setProperty("offset.storage.redis.key", "debezium:offsets:" + debeziumProperties.getServiceName());
        props.setProperty("offset.flush.interval.ms", "600000");


    }

    private void setFileStorageProperties(Properties props) {
        props.setProperty("database.history", FileDatabaseHistoryStore.class.getName());
        props.setProperty("database.history.redis.key", "debezium:db-history:" + debeziumProperties.getServiceName());
    }

    private void receiveChangeEvent(String value) {
        DatabaseBaseDTO databaseBaseDTO = DatabaseBaseDTO.parse(value);
        JSONObject payload = databaseBaseDTO.getPayload();
        if (payload == null) {
            return;
        }
        String op = payload.getString("op");
        if (op == null || op.isEmpty()) {
            return;
        }

        JSONObject before = databaseBaseDTO.getBefore();
        JSONObject after = databaseBaseDTO.getAfter();
        Set<DatabaseChangesCallback<T>> databaseChangesCallbacks = callbacks.get(databaseBaseDTO.getTableName());
        if (databaseChangesCallbacks != null) {
            for (DatabaseChangesCallback<T> callback : databaseChangesCallbacks) {
                if ("r".equals(op)) {
                    return;
                }
                callback.onChange();
                Class<T> entityClass = callback.getEntityClass();
                if (entityClass == null){
                    return;
                }
                T beforeEntity = before == null ? null : before.toJavaObject(entityClass);
                T afterEntity = after == null ? null : after.toJavaObject(entityClass);

                switch (op) {
                    case "u" -> {
                        callback.onUpdate(beforeEntity, afterEntity);
                        if (callback.isChanged(afterEntity, beforeEntity)) {
                            callback.onChanged(beforeEntity);
                        }
                    }
                    case "c" -> {
                        callback.onInsert(beforeEntity, afterEntity);
                        if (callback.isChanged(afterEntity, beforeEntity)) {
                            callback.onChanged(afterEntity);
                        }
                    }
                    case "d" -> {
                        callback.onDelete(beforeEntity, afterEntity);
                        if (callback.isChanged(afterEntity, beforeEntity)) {
                            callback.onChanged(beforeEntity);
                        }
                    }
                    default -> log.warn("未知的 binlog op 类型，op: {}, payload: {}", op, payload);
                }
            }
        }
    }

}
