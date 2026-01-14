package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private Device device;
    @TableField(exist = false)
    private Sensor sensor;

}
