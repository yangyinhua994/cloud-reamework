package com.example.convert;

import com.example.dto.ComponentDTO;
import com.example.dto.DeviceDTO;
import com.example.entity.Component;
import com.example.entity.Device;
import com.example.vo.ComponentVO;
import com.example.vo.DeviceVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ComponentConvert extends BaseConvert<Component, ComponentDTO, ComponentVO> {
    @Override
    @Mapping(source = "sensorDTOList", target = "sensorList")
    Component dtoToEntity(ComponentDTO dto);
}
