package com.example.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
     * 传感器信息
     */
    private List<SensorVO> sensorVOList;

}
