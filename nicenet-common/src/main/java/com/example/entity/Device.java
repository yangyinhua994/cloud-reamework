package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    private List<Component> componentList;
}
