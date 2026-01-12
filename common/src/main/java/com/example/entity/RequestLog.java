package com.example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestLog extends BaseEntity {

    /**
     * 类名
     */
    private String className;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 耗时（毫秒）
     */
    private Long time;

}
