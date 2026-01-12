package com.example.acpectj;

import com.example.client.SystemClient;
import com.example.dto.UserApiInfoDTO;
import com.example.entity.User;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.holder.UserContextHolder;
import com.example.response.Response;
import com.example.utils.CollectionUtils;
import com.example.utils.StringUtils;
import com.example.vo.UserApiInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor

public class PreAuthorizeAspect {

    private final SystemClient systemClient;

    @Pointcut("@annotation(com.example.annotation.PreAuthorize)")
    public void loginAdminPointcut() {
    }

    @Around("loginAdminPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        User user = UserContextHolder.getUser();
        if (user == null || user.getId() == null) {
            ApiException.error(ResponseMessageEnum.NO_AUTHORITY);
        }
        Long userId = user.getId();
        String apiUrl = joinPoint.getSignature().toShortString();
        if (StringUtils.isBlank(apiUrl)) {
            ApiException.error(ResponseMessageEnum.NO_AUTHORITY);
        }
        UserApiInfoDTO userApiInfoDTO = UserApiInfoDTO.builder()
                .userId(userId)
                .apiUrl(apiUrl)
                .build();
        Response<List<UserApiInfoVO>> response = systemClient.list(userApiInfoDTO);
        if (response.isFail() || CollectionUtils.isEmpty(response.getData())) {
            ApiException.error(ResponseMessageEnum.NO_AUTHORITY);
        }
        return joinPoint.proceed();
    }

}
