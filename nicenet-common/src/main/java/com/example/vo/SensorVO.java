package com.example.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SensorVO extends BaseVO {

    /**
     * 传感器编号
     */
    private String sensorNumber;

    /**
     * 传感器名称
     */
    private String sensorName;

    /**
     * 标识符号
     */
    private String symbol;
    /**
     * 参数名称
     */
    private String paramName;

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
     * 单位
     */
    private String unit;

    /**
     * 当前值
     */
    private BigDecimal currentValue;

    /**
     * 说明
     */
    private String remark;

}
