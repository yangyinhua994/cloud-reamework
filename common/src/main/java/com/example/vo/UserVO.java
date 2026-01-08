package com.example.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserVO extends BaseVO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户类型 1：管理员
     */
    private Integer userType;

    /**
     * 刷新 token
     */
    private String refreshToken;

    /**
     * 访问 token
     */
    private String token;
}
