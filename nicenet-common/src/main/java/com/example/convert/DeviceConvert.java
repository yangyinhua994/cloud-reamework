package com.example.convert;

import com.example.dto.DeviceDTO;
import com.example.entity.Device;
import com.example.vo.DeviceVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeviceConvert extends BaseConvert<Device, DeviceDTO, DeviceVO> {

    @Override
    @Mapping(source = "componentDTOList", target = "componentList")
    Device dtoToEntity(DeviceDTO dto);
}
