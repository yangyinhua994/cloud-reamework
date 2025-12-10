package com.example.properties;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Data
@Component
@Slf4j
public class DebeziumProperties {

    @Getter
    @Setter
    private static DebeziumProperties instance;

    @Value("${spring.datasource.addr}")
    private String datasourceAddr;
    @Value("${spring.datasource.port}")
    private Integer datasourcePort;
    @Value("${spring.datasource.username}")
    private String datasourceUserName;
    @Value("${spring.datasource.password}")
    private String datasourcePassword;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.database}")
    private String datasourceDatabase;


    @Value("${spring.data.redis.host:null}")
    private String redisHost;
    @Value("${spring.data.redis.port:6379}")
    private Integer redisPort;
    @Value("${spring.data.redis.password:null}")
    private String redisPassword;
    @Value("${spring.data.redis.username:null}")
    private String redisUsername;
    @Value("${spring.data.redis.database:0}")
    private Integer redisDatabase;

    @Value("${spring.application.name}")
    private String serviceName;

    @PostConstruct
    public void init() {
        setInstance(this);
        log.info("DebeziumProperties info: {}", this);
    }

}
