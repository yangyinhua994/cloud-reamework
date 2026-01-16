package com.example.convert;

import com.example.dto.DeviceComponentSensorDTO;
import com.example.entity.DeviceComponentSensor;
import com.example.vo.DeviceComponentSensorVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeviceComponentSensorConvert extends BaseConvert<DeviceComponentSensor, DeviceComponentSensorDTO, DeviceComponentSensorVO> {
}
