package com.example.dto;

import com.example.groups.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserDTO extends BaseDTO {

    /**
     * 用户名
     */
    @NotEmpty(message = "密码不能为空", groups = {Add.class, Login.class, Register.class})
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空", groups = {Add.class, Login.class, Register.class})
    private String password;

    /**
     * 用户类型 1：管理员
     */
    @NotEmpty(message = "密码不能为空", groups = {Add.class, Register.class})
    private Integer userType;
}
