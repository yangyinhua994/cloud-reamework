package com.example.client;

import com.example.response.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "finance-service", path = "/finance")
public interface FinanceClient {

    @PostMapping("/register")
    Response<?> register();

}
