package com.example.convert;

import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.vo.SensorVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {DeviceConvert.class})
public interface SensorConvert extends BaseConvert<Sensor, SensorDTO, SensorVO> {

    @Override
    @Mapping(source = "deviceDTOList", target = "deviceList")
    @Mapping(source = "sensorParamDTOList", target = "sensorParamList")
    Sensor dtoToEntity(SensorDTO dto);

}

