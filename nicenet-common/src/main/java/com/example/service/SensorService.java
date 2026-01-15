package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.vo.SensorVO;

import java.util.List;

public interface SensorService extends BaseService<Sensor> {
    boolean exists(List<Long> sensorIds);

    void checkIds(List<Long> ids);

    void checkSensorIds(List<SensorDTO> dtoList);

    void preAddList(List<SensorDTO> dtoList);

    List<SensorVO> list(SensorDTO dto);

    Page<SensorVO> page(SensorDTO dto);

    List<Sensor> getBySensorNumbers(List<String> sensorNumbers);

    void preList(SensorDTO dto, LambdaQueryWrapper<Sensor> queryWrapper);

    void postAddList(List<Sensor> sensors);

    void preUpdate(SensorDTO dto);

    void postUpdate(Sensor entity);

    void postDelete(Long id);

    void preReturn(List<SensorVO> sensorVOList);
}
