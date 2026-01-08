package com.example.acpectj;

import com.example.entity.User;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.holder.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoginAdminAspect {

    @Pointcut("@annotation(com.example.annotation.LoginAdmin)")
    public void loginAdminPointcut() {
    }

    @Around("loginAdminPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        validateAdminLogin();

        return joinPoint.proceed();
    }

    private void validateAdminLogin() {
        User user = UserContextHolder.getUser();
        if (user == null || !user.isAdmin()) {
            ApiException.error(ResponseMessageEnum.NO_AUTHORITY);
        }
    }
}
