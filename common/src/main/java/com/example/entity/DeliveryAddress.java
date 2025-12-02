package com.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeliveryAddress extends BaseEntity {
    private Long userId;

    private Long communityId;

    private String recipientName;

    private String recipientPhone;

    private String detailAddress;

    private Integer isDefault = 0;

}
