package com.example.dto;

import com.example.groups.Add;
import com.example.groups.Delete;
import com.example.groups.Update;
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
    @NotEmpty(message = "用户名不能为空", groups = {Add.class, Update.class, Delete.class})
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空", groups = {Add.class})
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别(0:未知,1:男,2:女)
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空", groups = {Add.class})
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确", groups = {Add.class, Update.class})
    private String phone;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 状态(0:禁用,1:启用)
     */
    private Integer status;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;
}
