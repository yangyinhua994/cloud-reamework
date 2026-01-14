package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Device extends BaseEntity {

    private String deviceNumber;
    private String deviceName;
    private String remark;

    @TableField(exist = false)
    private Component component;
    @TableField(exist = false)
    private Sensor sensor;
}
