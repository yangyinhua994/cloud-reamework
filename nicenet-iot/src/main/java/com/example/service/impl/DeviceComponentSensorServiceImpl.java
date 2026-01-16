package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.*;
import com.example.entity.*;
import com.example.mapper.DeviceComponentSensorMapper;
import com.example.service.DeviceComponentSensorService;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import com.example.vo.DeviceComponentSensorVO;
import com.example.wrapper.NotNollLambdaQueryWrapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceComponentSensorServiceImpl extends BaseServiceImpl<DeviceComponentSensorMapper, DeviceComponentSensor> implements DeviceComponentSensorService {


    private final DeviceComponentSensorMapper deviceComponentSensorMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addByDevice(Device entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        addByDevice(List.of(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addByDevice(List<Device> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }

        List<DeviceComponentSensor> list = buildByDevices(entityList);
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        saveBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addByComponent(Component entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        addByComponent(List.of(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addByComponent(List<Component> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }

        List<DeviceComponentSensor> deviceComponentSensors = buildByComponents(entityList);
        if (CollectionUtils.isEmpty(deviceComponentSensors)) {
            return;
        }

        saveBatch(deviceComponentSensors);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBySensor(Sensor entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        addBySensor(List.of(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBySensor(List<Sensor> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        List<DeviceComponentSensor> deviceComponentSensors = buildBySensor(entityList);
        if (CollectionUtils.isEmpty(deviceComponentSensors)) {
            return;
        }

        saveBatch(deviceComponentSensors);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBySensorParam(SensorParam entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        addBySensorParams(List.of(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBySensorParams(List<SensorParam> entityList) {
        List<DeviceComponentSensor> deviceComponentSensors = buildBySensorParams(entityList);
        if (CollectionUtils.isEmpty(deviceComponentSensors)) {
            return;
        }
        saveBatch(deviceComponentSensors);
    }

    @Override
    public List<DeviceComponentSensor> buildBySensorParams(List<SensorParam> entityList) {
        List<DeviceComponentSensor> deviceComponentSensors = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return deviceComponentSensors;
        }
        for (SensorParam sensorParam : entityList) {
            Long sensorParamId = sensorParam.getId();
            if (ObjectUtils.isEmpty(sensorParamId)) {
                continue;
            }
            List<Sensor> sensorList = sensorParam.getSensorList();
            if (CollectionUtils.isEmpty(sensorList)) {
                continue;
            }
            for (Sensor sensor : sensorList) {
                Long sensorId = sensor.getId();
                if (ObjectUtils.isEmpty(sensorId)) {
                    continue;
                }
                DeviceComponentSensor componentSensor = build(null, null, sensorId, sensorParamId);
                if (ObjectUtils.isEmpty(componentSensor)) {
                    continue;
                }
                deviceComponentSensors.add(componentSensor);
            }
        }
        return deviceComponentSensors;
    }

    @Override
    public List<DeviceComponentSensor> buildByDevices(List<Device> entityList) {
        List<DeviceComponentSensor> deviceComponentSensors = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return deviceComponentSensors;
        }
        for (Device device : entityList) {
            Long deviceId = device.getId();
            if (ObjectUtils.isEmpty(deviceId)) {
                continue;
            }
            List<Component> componentList = device.getComponentList();
            if (CollectionUtils.isEmpty(componentList)) {
                continue;
            }

            List<DeviceComponentSensor> list = buildByComponents(deviceId, componentList);
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }
            deviceComponentSensors.addAll(list);
        }
        return deviceComponentSensors;
    }

    @Override
    public List<DeviceComponentSensor> buildByComponents(List<Component> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return new ArrayList<>();
        }
        List<DeviceComponentSensor> deviceComponentSensors = buildByComponents(null, entityList);
        for (Component component : entityList) {
            Long componentId = component.getId();
            if (ObjectUtils.isEmpty(componentId)) {
                continue;
            }
            List<Device> deviceList = component.getDeviceList();
            if (CollectionUtils.isEmpty(deviceList)) {
                continue;
            }
            for (Device device : deviceList) {
                Long deviceId = device.getId();
                if (ObjectUtils.isEmpty(deviceId)) {
                    continue;
                }
                DeviceComponentSensor deviceComponentSensor = build(deviceId, componentId, null, null);
                if (ObjectUtils.isEmpty(deviceComponentSensor)) {
                    continue;
                }
                deviceComponentSensors.add(deviceComponentSensor);
            }
        }

        return deviceComponentSensors;
    }

    @Override
    public List<DeviceComponentSensor> buildByComponents(Long deviceId, List<Component> entityList) {
        List<DeviceComponentSensor> deviceComponentSensors = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return deviceComponentSensors;
        }
        for (Component component : entityList) {
            Long componentId = component.getId();
            if (ObjectUtils.isEmpty(componentId)) {
                continue;
            }

            List<DeviceComponentSensor> list = buildBySensor(deviceId, component.getId(), component.getSensorList());
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }
            deviceComponentSensors.addAll(list);
        }
        return deviceComponentSensors;
    }

    @Override
    public List<DeviceComponentSensor> buildBySensor(List<Sensor> entityList) {
        List<DeviceComponentSensor> deviceComponentSensors = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return deviceComponentSensors;
        }
        for (Sensor sensor : entityList) {
            List<Device> deviceList = sensor.getDeviceList();
            if (CollectionUtils.isEmpty(deviceList)) {
                continue;
            }
            for (Device device : deviceList) {
                Long deviceId = device.getId();
                if (ObjectUtils.isEmpty(deviceId)) {
                    continue;
                }

                List<Component> componentList = device.getComponentList();
                if (CollectionUtils.isEmpty(componentList)) {
                    // 说明该设备没有绑定部件，直接存储设备和传感器的数据
                    DeviceComponentSensor componentSensor = build(deviceId, null, sensor.getId(), null);
                    if (ObjectUtils.isEmpty(componentSensor)) {
                        continue;
                    }
                    deviceComponentSensors.add(componentSensor);
                    continue;
                }
                for (Component component : componentList) {
                    Long componentId = component.getId();
                    if (ObjectUtils.isEmpty(componentId)) {
                        continue;
                    }
                    DeviceComponentSensor componentSensor = build(deviceId, componentId, sensor.getId(), null);
                    if (ObjectUtils.isEmpty(componentSensor)) {
                        continue;
                    }
                    deviceComponentSensors.add(componentSensor);
                }
            }
        }
        return deviceComponentSensors;
    }

    @Override
    public List<DeviceComponentSensor> buildBySensor(Long deviceId, Long ComponentId, List<Sensor> entityList) {
        List<DeviceComponentSensor> deviceComponentSensors = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return deviceComponentSensors;
        }
        for (Sensor sensor : entityList) {
            Long sensorId = sensor.getId();
            if (ObjectUtils.isEmpty(sensorId)) {
                continue;
            }
            List<SensorParam> sensorParamList = sensor.getSensorParamList();
            if (CollectionUtils.isEmpty(sensorParamList)) {
                DeviceComponentSensor componentSensor = build(deviceId, ComponentId, sensorId, null);
                if (ObjectUtils.isNotEmpty(componentSensor)) {
                    deviceComponentSensors.add(componentSensor);
                }
            } else {
                for (SensorParam sensorParam : sensorParamList) {
                    Long sensorParamId = sensorParam.getId();
                    if (ObjectUtils.isNotEmpty(sensorParamId)) {
                        DeviceComponentSensor componentSensor = build(deviceId, ComponentId, sensorId, sensorParamId);
                        if (ObjectUtils.isNotEmpty(componentSensor)) {
                            deviceComponentSensors.add(componentSensor);
                        }
                    }

                }
            }
        }
        return deviceComponentSensors;
    }

    @Override
    public DeviceComponentSensor build(Long deviceId, Long ComponentId, Long sensorId, Long sensorParamId) {
        if (ObjectUtils.isAllEmpty(deviceId, ComponentId, sensorId, sensorParamId)) {
            return null;
        }
        return DeviceComponentSensor.builder()
                .deviceId(deviceId)
                .componentId(ComponentId)
                .sensorId(sensorId)
                .sensorParamId(sensorParamId)
                .build();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeBySensor(SensorDTO sensorDTO) {
        if (ObjectUtils.isEmpty(sensorDTO)) {
            return;
        }

        this.remove(null, null, sensorDTO.getId(), null);
    }

    @Override
    public void removeBySensorParam(SensorParamDTO sensorDTO) {
        if (ObjectUtils.isEmpty(sensorDTO)) {
            return;
        }
        this.remove(null, null, null, sensorDTO.getId());
    }

    @Override
    public void removeByDevice(DeviceDTO deviceDTO) {
        if (ObjectUtils.isEmpty(deviceDTO)) {
            return;
        }
        this.remove(deviceDTO.getId(), null, null, null);
    }

    @Override
    public void removeByComponent(ComponentDTO componentDTO) {
        if (ObjectUtils.isEmpty(componentDTO)) {
            return;
        }
        this.remove(null, componentDTO.getId(), null, null);
    }

    @Override
    public void removeBySensorId(Long sensorId) {
        if (ObjectUtils.isEmpty(sensorId)) {
            return;
        }
        remove(null, null, sensorId, null);
    }

    @Override
    public void removeBySensorParamId(Long sensorParamId) {
        if (ObjectUtils.isEmpty(sensorParamId)) {
            return;
        }
        remove(null, null, null, sensorParamId);
    }

    @Override
    public void remove(Long deviceId, Long componentId, Long sensorId, Long sensorParamId) {
        if (ObjectUtils.isAllEmpty(deviceId, componentId, sensorId, sensorParamId)) {
            return;
        }
        LambdaQueryWrapper<DeviceComponentSensor> queryWrapper = new NotNollLambdaQueryWrapper<>(DeviceComponentSensor.class)
                .eq(DeviceComponentSensor::getDeviceId, deviceId)
                .eq(DeviceComponentSensor::getComponentId, componentId)
                .eq(DeviceComponentSensor::getSensorId, sensorId)
                .eq(DeviceComponentSensor::getSensorParamId, sensorParamId);
        this.remove(queryWrapper);
    }

    @Override
    public List<DeviceComponentSensorVO> listData(DeviceComponentSensorDTO dto) {
        return deviceComponentSensorMapper.listData(dto);
    }

    @Override
    public Page<DeviceComponentSensorVO> pageData(DeviceComponentSensorDTO dto) {
        return deviceComponentSensorMapper.pageData(Page.of(dto.getPageNum(), dto.getPageSize()), dto);
    }

}
