package com.example.convert;

import com.example.dto.SensorParamDTO;
import com.example.entity.SensorParam;
import com.example.vo.SensorParamVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {DeviceConvert.class})
public interface SensorParamConvert extends BaseConvert<SensorParam, SensorParamDTO, SensorParamVO> {


}

