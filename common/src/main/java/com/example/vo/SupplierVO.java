package com.example.vo;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
// 不要加Builder注解，否则mapstruct不会生成构造父类属性的代码
// @Builder
public class SupplierVO extends BaseVO {

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
