package com.example.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DeliveryAddressVO extends BaseVO {
    
    private Long userId;
    
    private Long communityId;
    
    private String recipientName;
    
    private String recipientPhone;
    
    private String detailAddress;
    
    private Integer isDefault;
}
