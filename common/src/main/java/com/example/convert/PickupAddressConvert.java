package com.example.convert;

import com.example.dto.PickupAddressDTO;
import com.example.entity.PickupAddress;
import com.example.vo.PickupAddressVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PickupAddressConvert extends BaseConvert<PickupAddress, PickupAddressDTO, PickupAddressVO> {
}
