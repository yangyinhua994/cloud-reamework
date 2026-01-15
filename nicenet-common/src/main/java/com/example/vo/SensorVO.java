package com.example.vo;

import com.example.entity.Device;
import com.example.entity.SensorParam;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SensorVO extends BaseVO {

    /**
     * 传感器编号
     */
    private String sensorNumber;

    /**
     * 传感器名称
     */
    private String sensorName;
    /**
     * 说明
     */
    private String remark;

    /**
     * 设备列表
     */
    private List<DeviceVO> deviceVOList;

    /**
     * 传感器参数数据列表
     */
    private List<SensorParamVO> sensorParamVOList;

}
