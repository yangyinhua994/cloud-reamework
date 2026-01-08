package com.example.controller;

import com.example.annotation.LoginAdmin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class TestController {

    @GetMapping("/test")
    @LoginAdmin()
    public String hello() {
        return "hello world";
    }

}
