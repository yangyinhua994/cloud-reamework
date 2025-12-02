package com.example.config;

import com.example.interceptor.UserContextInterceptor;
import com.example.properties.AppConfigSecurityIgnoreConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserContextInterceptor userContextInterceptor;
    private final AppConfigSecurityIgnoreConfigProperties appConfigSecurityIgnoreConfigProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        registry.addInterceptor(userContextInterceptor)
                .excludePathPatterns(appConfigSecurityIgnoreConfigProperties.getUrls())
                .addPathPatterns("/**");
    }

}
