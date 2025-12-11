package com.example.parser;

import com.example.holder.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestOriginParser implements com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest httpServletRequest) {
        return UserContextHolder.getServerName();
    }
}
