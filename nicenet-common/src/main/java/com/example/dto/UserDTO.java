package com.example.dto;

import com.example.groups.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {

    /**
     * 用户名
     */
    @NotBlank(message = "密码不能为空", groups = {Add.class, Login.class, Register.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = {Add.class, Login.class, Register.class})
    private String password;

    /**
     * 用户类型 1：管理员
     */
    @NotNull(message = "密码不能为空", groups = {Add.class, Register.class})
    private Integer userType;

    /**
     * 刷新token
     */
    @NotBlank(message = "刷新token不能为空", groups = {RefreshToken.class})
    private String refreshToken;
}
