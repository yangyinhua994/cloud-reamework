package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("delivery_address")
public class DeliveryAddress extends BaseEntity {
    private Long userId;

    private Long communityId;

    private String recipientName;

    private String recipientPhone;

    private String detailAddress;

    private Integer isDefault = 0;

}
