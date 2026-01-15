package com.example.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.vo.SensorVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SensorMapper extends BaseMapper<Sensor> {
    List<SensorVO> list(@Param("dto") SensorDTO dto);

    Page<SensorVO> page(@Param("page") Page<Object> page, @Param("dto") SensorDTO dto);
}
