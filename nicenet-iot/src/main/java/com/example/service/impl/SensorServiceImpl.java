package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convert.SensorConvert;
import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.SensorMapper;
import com.example.service.DeviceComponentSensorService;
import com.example.service.SensorService;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import com.example.vo.SensorVO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl extends BaseServiceImpl<SensorMapper, Sensor> implements SensorService {

    private final SensorConvert sensorConvert;
    private final DeviceComponentSensorService deviceComponentSensorService;
    private final SensorMapper sensorMapper;

    @Override
    public boolean exists(List<Long> sensorIds) {

        if (CollectionUtils.isEmpty(sensorIds)) {
            return false;
        }
        LambdaQueryWrapper<Sensor> queryWrapper = new LambdaQueryWrapper<Sensor>()
                .in(Sensor::getId, sensorIds);
        return super.count(queryWrapper) == sensorIds.size();

    }

    @Override
    public void checkIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        if (!exists(ids)) {
            ApiException.error(ResponseMessageEnum.SENSOR_NOT_EXIST);
        }
    }

    @Override
    public void checkSensorIds(List<SensorDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        checkIds(dtoList.stream().map(SensorDTO::getId).filter(ObjectUtils::isNotEmpty).toList());
    }

    @Override
    public List<Sensor> preAddList(List<SensorDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return List.of();
        }
        List<String> sensorNumbers = dtoList.stream().map(SensorDTO::getSensorNumber).filter(ObjectUtils::isNotEmpty).toList();
        List<Sensor> sensors = getBySensorNumbers(sensorNumbers);
        if (ObjectUtils.isNotEmpty(sensors)) {
            String sensorNumber = sensors.get(0).getSensorNumber();
            ApiException.error("传感器编号重复: " + sensorNumber);
        }
        return sensorConvert.dtoToEntity(dtoList);
    }

    @Override
    public List<SensorVO> list(SensorDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return List.of();
        }
        return sensorMapper.list(dto);
    }

    @Override
    public Page<SensorVO> page(SensorDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }
        return sensorMapper.page(Page.of(dto.getPageNum(), dto.getPageSize()), dto);
    }

    @Override
    public List<Sensor> getBySensorNumbers(List<String> sensorNumbers) {
        if (CollectionUtils.isEmpty(sensorNumbers)) {
            return List.of();
        }
        LambdaQueryWrapper<Sensor> queryWrapper = new LambdaQueryWrapper<Sensor>()
                .in(Sensor::getSensorNumber, sensorNumbers);
        return this.list(queryWrapper);
    }

}
