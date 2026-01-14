package com.example.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ComponentVO extends BaseVO {

    /**
     * 部件编号
     */
    private String componentNumber;

    /**
     * 部件名称
     */
    private String componentName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 设备信息
     */
    private DeviceVO deviceVO;

    /**
     * 传感器信息
     */
    private SensorVO sensorVO;
}
