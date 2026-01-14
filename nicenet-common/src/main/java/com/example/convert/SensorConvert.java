package com.example.convert;

import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.vo.SensorVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SensorConvert extends BaseConvert<Sensor, SensorDTO, SensorVO> {
    @Override
    @Mapping(source = "deviceDTO", target = "device")
    @Mapping(source = "componentDTO", target = "component")
    Sensor dtoToEntity(SensorDTO dto);
}

