package com.example.client;

import com.example.response.Response;
import com.example.vo.OrderVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-server", path = "/order")
public interface OrderClient {

    @GetMapping("/query/{id}")
    Response<OrderVO> getOrderById(@PathVariable("id") Long id);

}
