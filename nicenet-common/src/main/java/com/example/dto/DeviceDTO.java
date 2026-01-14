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
     * 部件信息
     */
    private ComponentDTO componentDTO;

    /**
     * 传感器信息
     */
    private SensorDTO sensorDTO;

    /**
     * 说明
     */
    private String remark;

    public boolean equalsDeviceNumber(String deviceNumber) {
        return Objects.equals(this.deviceNumber, deviceNumber);
    }

}
