package com.example.acpectj;

import com.example.entity.RequestLog;
import com.example.mapper.RequestLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.config.aspect.request.log.enabled", havingValue = "true")
public class LogAspect {

    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new ThreadLocal<>();
    private final RequestLogMapper requestLogMapper;

    @Before(value = "execution(* com.example.controller.*.*(..))")
    public void before() {
        handlerBefore();
    }

    @AfterReturning(value = "execution(* com.example.controller.*.*(..))", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        handlerAfterReturning(joinPoint, result);
    }

    public void handlerBefore() {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    @Async
    public void handlerAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getName();
        RequestLog requestLog = RequestLog.builder()
                .className(className)
                .methodName(joinPoint.getSignature().getName())
                .time(System.currentTimeMillis() - TIME_THREAD_LOCAL.get())
                .build();
        saveRequestLogAsync(requestLog);
    }

    public void saveRequestLogAsync(RequestLog requestLog) {
        try {
            requestLogMapper.insert(requestLog);
        } catch (Exception e) {
            log.error("异步保存请求日志失败: {}", e.getMessage(), e);
        }
    }

}
