package com.example.convert;

import com.example.dto.OrderDTO;
import com.example.entity.Order;
import com.example.vo.OrderVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderConvert extends BaseConvert<Order, OrderDTO, OrderVO> {
}
