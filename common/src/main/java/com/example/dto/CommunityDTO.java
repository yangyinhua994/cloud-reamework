package com.example.dto;

import com.example.groups.Add;
import com.example.groups.Update;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

/**
 * 小区DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDTO extends BaseDTO {

    /**
     * 小区名称
     */
    @NotEmpty(message = "小区名称不能为空", groups = {Add.class})
    private String name;

    /**
     * 详细地址
     */
    @NotEmpty(message = "详细地址不能为空", groups = {Add.class})
    private String address;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 状态(0:未入驻,1:已入驻)
     */
    @NotNull(message = "状态不能为空", groups = {Add.class})
    private Integer status;
}
