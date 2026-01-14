package com.example.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.DeviceComponentSensorDTO;
import com.example.entity.DeviceComponentSensor;
import com.example.vo.DeviceComponentSensorVO;

import java.util.List;

public interface DeviceComponentSensorMapper extends BaseMapper<DeviceComponentSensor> {
    List<DeviceComponentSensorVO> list(DeviceComponentSensorDTO dto);

    Page<DeviceComponentSensorVO> page(Page<Object> of, DeviceComponentSensorDTO dto);
}
