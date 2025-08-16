package com.example.dto;

import com.example.groups.Add;
import com.example.groups.Update;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 取货地址DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PickupAddressDTO extends BaseDTO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = {Add.class})
    private Long userId;

    /**
     * 小区ID
     */
    @NotNull(message = "小区ID不能为空", groups = {Add.class})
    private Long communityId;

    /**
     * 取件点名称
     */
    @NotEmpty(message = "取件点名称不能为空", groups = {Add.class})
    private String pickupPoint;

    /**
     * 详细地址
     */
    @NotEmpty(message = "详细地址不能为空", groups = {Add.class})
    private String detailAddress;

    /**
     * 是否默认取件点(0:否,1:是)
     */
    private Integer isDefault = 0;
}
