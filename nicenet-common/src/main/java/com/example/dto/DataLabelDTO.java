package com.example.dto;

import com.example.groups.Add;
import com.example.groups.UpdateById;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DataLabelDTO extends BaseDTO {

    /**
     * 设备部件传感器关联表id
     */
    @NotNull(message = "关联数据ID不能为空", groups = {Add.class, UpdateById.class})
    private Long deviceComponentSensorId;

    /**
     * 故障时间点
     */
    @NotNull(message = "故障时间点不能为空", groups = {Add.class, UpdateById.class})
    private LocalDateTime faultTime;

    /**
     * 异常原因
     */
    @Size(message = "异常原因长度不能超过255", max = 255)
    private String errorCause;

}
