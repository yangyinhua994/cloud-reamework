package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("community")
public class Community extends BaseEntity {
    private String name;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String contactPerson;

    private String contactPhone;

    private Integer status = 1;

}
