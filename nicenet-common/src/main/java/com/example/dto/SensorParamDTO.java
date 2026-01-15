package com.example.dto;

import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.example.entity.BaseEntity;
import com.example.groups.Add;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SensorParamDTO extends BaseDTO {

    /**
     * 标识符号
     */
    private String symbol;

    /**
     * 数据类型字典id
     */
    private Long dataTypeId;

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
