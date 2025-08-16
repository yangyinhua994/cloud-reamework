package com.example.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PickupAddressVO extends BaseVO {
    
    private Long userId;
    
    private Long communityId;
    
    private String pickupPoint;
    
    private String detailAddress;
    
    private Integer isDefault;
}
