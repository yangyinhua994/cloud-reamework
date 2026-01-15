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
public class DeviceVO extends BaseVO {
    /**
     * 设备编号
     */
    private String deviceNumber;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 部件信息
     */
    private List<ComponentVO> componentVOList;

}
