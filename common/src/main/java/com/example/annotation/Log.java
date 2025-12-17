package com.example.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author SuGuichuan
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 请求参数
     */
    String requestParam() default "";

}
