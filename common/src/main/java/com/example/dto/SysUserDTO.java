package com.example.dto;

import com.example.groups.Add;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 系统用户DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysUserDTO extends BaseDTO {

    /**
     * 用户名
     */
    @NotEmpty(message = "用户名不能为空", groups = {Add.class})
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空", groups = {Add.class})
    private String password;

    /**
     * 昵称
     */
    @NotEmpty(message = "昵称不能为空", groups = {Add.class})
    private String nickname;

    /**
     * 邮箱
     */
    @NotEmpty(message = "邮箱不能为空", groups = {Add.class})
    private String email;

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空", groups = {Add.class})
    private String phone;

    /**
     * 用户状态(0:禁用,1:启用)
     */
    @NotNull(message = "状态不能为空，0:禁用,1:启用", groups = {Add.class})
    private Integer status;

    /**
     * 头像
     */
    @NotEmpty(message = "头像不能为空", groups = {Add.class})
    private String avatar;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

}
