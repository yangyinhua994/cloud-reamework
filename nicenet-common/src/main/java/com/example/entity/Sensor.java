package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.example.vo.SensorParamVO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Sensor extends BaseEntity {

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

    @TableField(exist = false)
    private List<Device> deviceList;

    @TableField(exist = false)
    private List<SensorParam> sensorParamList;

}
