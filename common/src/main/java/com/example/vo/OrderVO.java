package com.example.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class OrderVO extends BaseVO {
    
    private Long userId;
    
    private Integer orderType;
    
    private Long deliveryAddressId;
    
    private Long pickupAddressId;
    
    private String serviceContent;
    
    private Integer status;
    
    private BigDecimal price;
}
