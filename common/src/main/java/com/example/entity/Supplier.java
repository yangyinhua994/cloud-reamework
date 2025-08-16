package com.example.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Supplier extends BaseEntity {

    /**
     * 供应商名称
     */
    @NotEmpty(message = "供应商名称不能为空")
    private String name;
    /**
     * 联系人信息
     */
    @NotEmpty(message = "联系人信息不能为空")
    private String contactInfo;
    /**
     * 供应商地址
     */
    @NotEmpty(message = "供应商地址不能为空")
    private String address;
    /**
     * 供应商电话
     */
    @NotEmpty(message = "供应商电话不能为空")
    private String phone;
    /**
     * 供应商邮箱
     */
    @NotEmpty(message = "供应商邮箱不能为空")
    private String email;
    /**
     * 供应商状态
     */
    @NotNull(message = "供应商状态不能为空")
    private Integer status;
}
