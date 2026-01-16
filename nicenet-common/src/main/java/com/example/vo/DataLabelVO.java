package com.example.vo;

import com.example.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DataLabelVO extends BaseVO{

    /**
     * 设备部件传感器关联表id
     */
    private Long deviceComponentSensorId;

    /**
     * 故障时间点
     */
    private LocalDateTime faultTime;

}
