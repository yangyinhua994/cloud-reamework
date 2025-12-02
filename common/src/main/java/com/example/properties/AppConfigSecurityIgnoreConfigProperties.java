package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.config.security.ignore")
public class AppConfigSecurityIgnoreConfigProperties {

    private List<String> urls;

}
