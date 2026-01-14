package com.example.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceComponentSensorDTO extends BaseDTO {

    private Long deviceId;
    private Long componentId;
    private Long sensorId;

}
