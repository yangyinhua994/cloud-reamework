package com.example.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.groups.Add;
import com.example.groups.UpdateById;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDTO extends BaseDTO {

    /**
     * 部件编号
     */
    @NotBlank(message = "部件编号不能为空", groups = {Add.class, UpdateById.class})
    @ExcelProperty(value = "部件号")
    private String componentNumber;

    /**
     * 部件名称
     */
    @NotBlank(message = "部件名称不能为空", groups = {Add.class, UpdateById.class})
    @ExcelProperty(value = "部件名称")
    private String componentName;

    /**
     * 说明
     */
    @ExcelProperty(value = "说明")
    private String remark;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 传感器信息
     */
    private List<SensorDTO> sensorDTOList;

    /**
     * 设备信息
     */
    private List<DeviceDTO> deviceDTOList;

}
