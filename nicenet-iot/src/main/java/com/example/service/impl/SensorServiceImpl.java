package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convert.SensorConvert;
import com.example.dto.DeviceDTO;
import com.example.dto.SensorDTO;
import com.example.entity.Device;
import com.example.entity.Sensor;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.SensorMapper;
import com.example.service.DeviceComponentSensorService;
import com.example.service.DeviceService;
import com.example.service.DictService;
import com.example.service.SensorService;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import com.example.vo.SensorParamVO;
import com.example.vo.SensorVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl extends BaseServiceImpl<SensorMapper, Sensor> implements SensorService {

    private final SensorConvert sensorConvert;
    private final DeviceComponentSensorService deviceComponentSensorService;
    private final SensorMapper sensorMapper;
    private final DictService dictService;

    @Lazy
    @Autowired
    private DeviceService deviceService;

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
    public void preAddList(List<SensorDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        List<String> sensorNumbers = dtoList.stream().map(SensorDTO::getSensorNumber).filter(ObjectUtils::isNotEmpty).toList();
        List<Sensor> sensors = getBySensorNumbers(sensorNumbers);
        if (ObjectUtils.isNotEmpty(sensors)) {
            String sensorNumber = sensors.get(0).getSensorNumber();
            ApiException.error("传感器编号重复: " + sensorNumber);
        }
        for (SensorDTO sensorDTO : dtoList) {
            List<DeviceDTO> deviceDTOList = sensorDTO.getDeviceDTOList();
            deviceService.checkIdByDTOList(deviceDTOList);
        }
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

    @Override
    public void preList(SensorDTO dto, LambdaQueryWrapper<Sensor> queryWrapper) {
        queryWrapper.eq(Sensor::getSensorNumber, dto.getSensorNumber())
                .like(Sensor::getSensorNumber, dto.getSensorName());
    }

    @Override
    public void postAddList(List<Sensor> sensors) {
        if (CollectionUtils.isEmpty(sensors)) {
            return;
        }
        deviceComponentSensorService.addBySensor(sensors);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void preUpdate(SensorDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return;
        }
        List<DeviceDTO> deviceDTOList = dto.getDeviceDTOList();
        if (CollectionUtils.isEmpty(deviceDTOList)) {
            return;
        }
        deviceService.checkIdByDTOList(deviceDTOList);
        deviceComponentSensorService.removeBySensor(dto);
    }

    @Override
    public void postUpdate(Sensor entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        deviceComponentSensorService.addBySensor(entity);
    }

    @Override
    public void postDelete(Long id) {
        deviceComponentSensorService.removeBySensorId(id);
    }

    @Override
    public void preReturn(List<SensorVO> sensorVOList) {
        if (CollectionUtils.isNotEmpty(sensorVOList)) {
            for (SensorVO sensorVO : sensorVOList) {
                if (ObjectUtils.isNotEmpty(sensorVO)) {
                    List<SensorParamVO> sensorParamVOList = sensorVO.getSensorParamVOList();
                    if (CollectionUtils.isNotEmpty(sensorParamVOList)) {
                        for (SensorParamVO sensorParamVO : sensorParamVOList) {
                            if (ObjectUtils.isNotEmpty(sensorParamVO.getUnit())) {
                                Long unitId = sensorParamVO.getUnitId();
                                if (ObjectUtils.isNotEmpty(unitId)) {
                                    sensorParamVO.setUnit(dictService.getDictValue(unitId));
                                }
                                Long dataTypeId = sensorParamVO.getDataTypeId();
                                if (ObjectUtils.isNotEmpty(dataTypeId)) {
                                    sensorParamVO.setDataType(dictService.getDictValue(dataTypeId));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
