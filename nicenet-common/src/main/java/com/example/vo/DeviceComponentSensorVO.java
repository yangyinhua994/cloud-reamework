package com.example.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceComponentSensorVO extends BaseVO {

    private Long deviceId;
    private Long componentId;
    private Long sensorId;

}
