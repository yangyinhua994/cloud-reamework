package com.example.dto;

import com.example.groups.Add;
import com.example.groups.Update;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

/**
 * 订单DTO
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO extends BaseDTO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = {Add.class})
    private Long userId;

    /**
     * 订单类型(1:帮取,2:帮送)
     */
    @NotNull(message = "订单类型不能为空", groups = {Add.class})
    private Integer orderType;

    /**
     * 收货地址ID
     */
    private Long deliveryAddressId;

    /**
     * 取货地址ID
     */
    private Long pickupAddressId;

    /**
     * 服务内容描述
     */
    private String serviceContent;

    /**
     * 订单状态(1:待接单,2:已接单,3:进行中,4:已完成,5:已取消)
     */
    private Integer status;

    /**
     * 服务价格
     */
    private BigDecimal price = BigDecimal.ZERO;
}
