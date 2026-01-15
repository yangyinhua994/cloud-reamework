package com.example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SensorParam extends BaseEntity {

    /**
     * 标识符号
     */
    private String symbol;
    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 数据类型字典id
     */
    private Long dataTypeId;

    /**
     * 最优值
     */
    private BigDecimal optimalValue;

    /**
     * 最小取值
     */
    private BigDecimal miniValue;

    /**
     * 最大取值
     */
    private BigDecimal maxValue;

    /**
     * 单位字典id
     */
    private Long unitId;

    /**
     * 当前值
     */
    private BigDecimal currentValue;

    /**
     * 说明
     */
    private String remark;

}
