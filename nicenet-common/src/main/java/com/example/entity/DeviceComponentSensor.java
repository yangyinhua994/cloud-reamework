package com.example.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceComponentSensor extends BaseEntity {

    private Long deviceId;
    private Long componentId;
    private Long sensorId;
    private Long sensorParamId;

    private String remark;

}
