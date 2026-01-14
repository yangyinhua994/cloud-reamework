package com.example.dto;

import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.example.groups.Add;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ComponentDTO extends BaseDTO {

    /**
     * 部件编号
     */
    @NotBlank(message = "部件编号不能为空", groups = {Add.class, Update.class})
    private String componentNumber;

    /**
     * 部件名称
     */
    @NotBlank(message = "部件名称不能为空", groups = {Add.class, Update.class})
    private String componentName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 设备信息
     */
    private DeviceDTO deviceDTO;

    /**
     * 传感器信息
     */
    private SensorDTO sensorDTO;

    public boolean equalsComponentNumber(String componentNumber) {
        return this.componentNumber.equals(componentNumber);
    }

}
