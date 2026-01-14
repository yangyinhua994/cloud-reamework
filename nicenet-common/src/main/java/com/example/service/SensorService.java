package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.vo.SensorVO;

import java.util.List;

public interface SensorService extends BaseService<Sensor> {
    boolean exists(List<Long> sensorIds);

    void checkIds(List<Long> ids);

    List<Sensor> preAddList(List<SensorDTO> dtoList);

    void postAddList( List<Sensor> sensors);

    List<SensorVO> list(SensorDTO dto);

    Page<SensorVO> page(SensorDTO dto);

    List<Sensor> getBySensorNumbers(List<String> sensorNumbers);

}
