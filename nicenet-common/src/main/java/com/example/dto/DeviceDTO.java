package com.example.dto;

import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.example.entity.DeviceComponentSensor;
import com.example.groups.Add;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO extends BaseDTO {
    /**
     * 设备编号
     */
    @NotBlank(message = "设备编号不能为空", groups = {Add.class, Update.class})
    private String deviceNumber;

    /**
     * 设备名称
     */
    @NotBlank(message = "设备名称不能为空", groups = {Add.class, Update.class})
    private String deviceName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 部件信息
     */
    private List<ComponentDTO> componentDTOList;

}
