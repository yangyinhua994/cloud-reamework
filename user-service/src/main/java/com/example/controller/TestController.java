package com.example.controller;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 测试
 */
@RestController
@RequestMapping("/user/test")
public class TestController {

    @GetMapping("/test")
    public String testSpEL(@RequestParam String expr) {
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(expr);
        return Objects.requireNonNull(expression.getValue()).toString();
    }

}
