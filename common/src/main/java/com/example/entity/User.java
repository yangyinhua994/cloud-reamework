package com.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class User extends BaseEntity {
    private String username;
    private String password;
    private String nickname;
    private String realName;
    private Integer gender = 0;
    private String avatar;

    private String email;
    private String phone;
    private String wechat;

    private Integer status = 1;
    private LocalDateTime lastLoginTime;
    private String lastLoginIp;

    private LocalDate birthday;
    private String address;
    private String remark;
}
