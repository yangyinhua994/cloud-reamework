package com.example.dto;

import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.example.groups.Add;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO extends BaseDTO {

    /**
     * 传感器编号
     */
    @NotBlank(message = "传感器编号不能为空", groups = {Add.class, Update.class})
    private String sensorNumber;

    /**
     * 传感器名称
     */
    @NotBlank(message = "传感器名称不能为空", groups = {Add.class, Update.class})
    private String sensorName;

    /**
     * 标识符号
     */
    @NotBlank(message = "标识符号不能为空", groups = {Add.class, Update.class})
    private String symbol;
    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空", groups = {Add.class, Update.class})
    private String paramName;

    /**
     * 最优值
     */
    @NotNull(message = "最优值不能为空", groups = {Add.class, Update.class})
    private BigDecimal optimalValue;

    /**
     * 最小取值
     */
    @NotNull(message = "最小取值不能为空", groups = {Add.class, Update.class})
    private BigDecimal miniValue;

    /**
     * 最大取值
     */
    @NotNull(message = "最大取值不能为空", groups = {Add.class, Update.class})
    private BigDecimal maxValue;

    /**
     * 单位
     */
    @NotBlank(message = "单位不能为空", groups = {Add.class, Update.class})
    private String unit;

    /**
     * 当前值
     */
    @NotNull(message = "当前值不能为空", groups = {Add.class, Update.class})
    private BigDecimal currentValue;

    /**
     * 说明
     */
    private String remark;

    /**
     * 设备信息
     */
    private DeviceDTO deviceDTO;

    /**
     * 部件信息
     */
    private ComponentDTO componentDTO;

    public boolean equalsSensorNumber(String sensorNumber) {
        return Objects.equals(this.sensorNumber, sensorNumber);
    }

}
