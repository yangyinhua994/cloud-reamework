package com.example.dto;

import com.example.groups.Add;
import com.example.groups.Login;
import com.example.groups.Register;
import com.example.groups.Update;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * 用户DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanceUserDTO extends BaseDTO {

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空", groups = {Add.class, Login.class, Register.class})
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确", groups = {Add.class, Update.class, Login.class, Register.class})
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空", groups = {Add.class, Login.class, Register.class})
    private String password;
}

