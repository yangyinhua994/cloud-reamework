package com.example.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.groups.Add;
import com.example.groups.UpdateById;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

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
    @NotBlank(message = "标识符号不能为空", groups = {Add.class, UpdateById.class})
    @ExcelProperty(value = "标识符号")
    private String symbol;

    /**
     * 参数名称
     */
    @ExcelProperty(value = "参数名称")
    private String paramName;

    /**
     * 数据类型字典id
     */
    @NotNull(message = "数据类型字典id不能为空", groups = {Add.class, UpdateById.class})
    private Long dataTypeId;

    @JsonIgnore
    @ExcelProperty(value = "数据类型")
    private String dataType;

    /**
     * 最优值
     */
    @ExcelProperty(value = "最优值")
    private BigDecimal optimalValue;

    /**
     * 最小取值
     */
    @ExcelProperty(value = "最小取值")
    private BigDecimal miniValue;

    /**
     * 最大取值
     */
    @ExcelProperty(value = "最大取值")
    private BigDecimal maxValue;

    /**
     * 单位字典id
     */
    private Long unitId;

    @JsonIgnore
    @ExcelProperty(value = "单位")
    private String unit;

    /**
     * 当前值
     */
    @ExcelProperty(value = "当前值")
    private BigDecimal currentValue;

    /**
     * 说明
     */
    @ExcelProperty(value = "说明")
    private String remark;

    /**
     * 传感器id
     */
    private Long sensorId;

    private List<SensorDTO> sensorDTOList;

}
