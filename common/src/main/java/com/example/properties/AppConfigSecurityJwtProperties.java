package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.config.security.jwt")
@Data
public class AppConfigSecurityJwtProperties {
    private String secret;
    private long expireMinute;
    private long refreshExpireMinute;
}
