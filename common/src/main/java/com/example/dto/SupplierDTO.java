package com.example.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 供应商信息DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO extends BaseDTO {

    /**
     * 供应商名称
     */
    private String name;
    /**
     * 联系人信息
     */
    private String contactInfo;
    /**
     * 供应商地址
     */
    private String address;
    /**
     * 供应商电话
     */
    private String phone;
    /**
     * 供应商邮箱
     */
    private String email;
    /**
     * 供应商状态
     */
    private Integer status;

}
