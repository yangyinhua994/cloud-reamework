package com.example.dto;

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
public class SensorDTO extends BaseDTO {

    /**
     * 传感器编号
     */
    @NotBlank(message = "传感器编号不能为空", groups = {Add.class, UpdateById.class})
    private String sensorNumber;

    /**
     * 传感器名称
     */
    @NotBlank(message = "传感器名称不能为空", groups = {Add.class, UpdateById.class})
    private String sensorName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 设备信息
     */
    private List<DeviceDTO> deviceDTOList;

    /**
     * 传感器参数信息
     */
    private List<SensorParamDTO> sensorParamDTOList;

}
