package com.example.convert;

import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.vo.SensorVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SensorConvert extends BaseConvert<Sensor, SensorDTO, SensorVO> {
}

