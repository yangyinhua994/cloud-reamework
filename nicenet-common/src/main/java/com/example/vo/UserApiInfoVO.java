package com.example.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserApiInfoVO extends BaseVO {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 接口地址
     */
    private String apiUrl;
}
