package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pickup_address")
public class PickupAddress extends BaseEntity {
    
    private Long userId;
    
    private Long communityId;
    
    private String pickupPoint;
    
    private String detailAddress;
    
    private Integer isDefault = 0;
}
