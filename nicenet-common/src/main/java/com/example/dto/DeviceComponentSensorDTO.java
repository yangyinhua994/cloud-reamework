package com.example.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceComponentSensorDTO extends BaseDTO {

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 部件ID
     */
    private Long componentId;

    /**
     * 传感器ID
     */
    private Long sensorId;

    /**
     * 传感器参数ID
     */
    private Long sensorParamId;

    /**
     * 备注
     */
    private String remark;

}
