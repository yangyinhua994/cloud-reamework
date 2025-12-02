package com.example.entity;

import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PickupAddress extends BaseEntity {

    private Long userId;

    private Long communityId;

    private String pickupPoint;

    private String detailAddress;

    private Integer isDefault = 0;
}
