package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserVO extends BaseVO {

    /**
     * 用户名
     */
    private String username;

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
