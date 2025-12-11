package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring")
@Data
public class SpringProperties {

    private Application application;

    @Data
    public static class Application {
        private String name;
    }

}
