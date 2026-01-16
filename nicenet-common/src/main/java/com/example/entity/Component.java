package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Component extends BaseEntity {

    private String componentNumber;
    private String componentName;
    private String remark;

    @TableField(exist = false)
    private List<Sensor> sensorList;

    @TableField(exist = false)
    private List<Device> deviceList;

}
