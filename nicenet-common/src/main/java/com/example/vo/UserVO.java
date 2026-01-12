package com.example.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
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
