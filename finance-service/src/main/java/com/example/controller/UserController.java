package com.example.controller;

import com.example.constant.GlobalTransactionalConstant;
import com.example.response.Response;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finance/user")
public class UserController {

    /**
     * 注册
     */
    @GlobalTransactional(name = GlobalTransactionalConstant.FinanceService.REGISTER, rollbackFor = Exception.class)
    @PostMapping("/register")
    public Response<?> register() {
        throw new RuntimeException("测试异常");
    }

}
