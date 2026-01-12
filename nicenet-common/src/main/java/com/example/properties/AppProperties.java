package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Config config;

    @Data
    @Component
    public static class Config {
        private Security security;
        private Boolean enableCache = false;

        @Data
        @Component
        public static class Security {
            private Jwt jwt;
            private Ignore ignore;

            @Data
            @Component
            public static class Jwt {
                private String secret = null;
                private Long expireMinute = 12000L;
            }

            @Data
            @Component
            public static class Ignore {
                private List<String> urls;
            }
        }

    }

}
