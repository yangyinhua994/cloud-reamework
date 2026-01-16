package com.example.dto;

import com.example.groups.Add;
import com.example.groups.UpdateById;
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
public class DeviceDTO extends BaseDTO {
    /**
     * 设备编号
     */
    @NotBlank(message = "设备编号不能为空", groups = {Add.class, UpdateById.class})
    private String deviceNumber;

    /**
     * 设备名称
     */
    @NotBlank(message = "设备名称不能为空", groups = {Add.class, UpdateById.class})
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
