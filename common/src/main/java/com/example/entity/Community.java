package com.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Community extends BaseEntity {
    private String name;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String contactPerson;

    private String contactPhone;

    private Integer status = 1;

}
