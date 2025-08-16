package com.example.dto;

import com.example.groups.Add;
import com.example.groups.Update;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 收货地址DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAddressDTO extends BaseDTO {

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
     * 收件人姓名
     */
    @NotEmpty(message = "收件人姓名不能为空", groups = {Add.class})
    private String recipientName;

    /**
     * 收件人电话
     */
    @NotEmpty(message = "收件人电话不能为空", groups = {Add.class})
    private String recipientPhone;

    /**
     * 详细地址
     */
    @NotEmpty(message = "详细地址不能为空", groups = {Add.class})
    private String detailAddress;

    /**
     * 是否默认地址(0:否,1:是)
     */
    private Integer isDefault = 0;
}
