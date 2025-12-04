package com.example.client;

import com.example.dto.FinanceUserDTO;
import com.example.response.Response;
import com.example.vo.FinanceUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "finance-server", path = "/finance")
public interface FinanceClient {

    @GetMapping("/user/register")
    Response<FinanceUserVO> register(@RequestBody FinanceUserDTO financeUserDTO);

}
