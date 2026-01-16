package com.example.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.DeviceComponentSensorDTO;
import com.example.entity.DeviceComponentSensor;
import com.example.vo.DeviceComponentSensorVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceComponentSensorMapper extends BaseMapper<DeviceComponentSensor> {
    List<DeviceComponentSensorVO> listData(@Param("dto") DeviceComponentSensorDTO dto);

    Page<DeviceComponentSensorVO> pageData(@Param("page") Page<Object> page, @Param("dto") DeviceComponentSensorDTO dto);
}
