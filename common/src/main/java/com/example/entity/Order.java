package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@TableName("orders")
public class Order extends BaseEntity {
    
    private Long userId;
    
    private Integer orderType;
    
    private Long deliveryAddressId;
    
    private Long pickupAddressId;
    
    private String serviceContent;
    
    private Integer status = 1;
    
    private BigDecimal price = BigDecimal.ZERO;
}
