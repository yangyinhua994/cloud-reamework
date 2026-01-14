package com.example.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.vo.SensorVO;

import java.util.List;

public interface SensorMapper extends BaseMapper<Sensor> {
    List<SensorVO> list(SensorDTO dto);

    Page<SensorVO> page(Page<Object> of, SensorDTO dto);
}
