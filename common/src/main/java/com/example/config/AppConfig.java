package com.example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@ConfigurationPropertiesScan("com.example.properties")
@EnableFeignClients(basePackages = "com.example.client")
public class AppConfig {

    private final Environment env;

    public boolean isDev() {
        return Arrays.asList(env.getActiveProfiles()).contains("dev");
    }
}
