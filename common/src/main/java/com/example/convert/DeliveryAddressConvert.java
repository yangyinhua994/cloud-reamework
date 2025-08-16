package com.example.convert;

import com.example.dto.DeliveryAddressDTO;
import com.example.entity.DeliveryAddress;
import com.example.vo.DeliveryAddressVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryAddressConvert extends BaseConvert<DeliveryAddress, DeliveryAddressDTO, DeliveryAddressVO> {
}
