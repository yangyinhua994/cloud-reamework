package com.example.handler;

import com.example.exception.ApiException;
import com.example.config.AppConfig;
import com.example.response.Response;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final AppConfig appConfig;

  /*  @ExceptionHandler(Exception.class)
    public Response<String> handleException(Exception ex) {
        return Response.fail(ex.getMessage());
    }*/

    @ExceptionHandler(ApiException.class)
    public Response<String> handleApiException(ApiException e) {
        /*String xid = RootContext.getXID();
        if (StringUtils.isNotBlank(xid)) {
            throw new ApiException(e.getMessage());
        }*/
        return Response.fail(e.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Response<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        if (appConfig.isDev()) {
            throw new RuntimeException(e);
        }
        if (e.getMessage().contains("foreign key constraint fails")) {
            return Response.fail("数据关联关系错误，请检查外键引用的数据是否存在");
        }
        return Response.fail("数据完整性约束违反: " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return Response.fail(errors.values().stream().findFirst().orElse("参数错误"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response<String> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return Response.fail(errors.values().stream().findFirst().orElse("参数错误"));
    }
}
