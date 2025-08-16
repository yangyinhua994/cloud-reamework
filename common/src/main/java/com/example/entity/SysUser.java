package com.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SysUser extends BaseEntity {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private Integer status;
    private String avatar;
    private LocalDateTime lastLoginTime;

}
