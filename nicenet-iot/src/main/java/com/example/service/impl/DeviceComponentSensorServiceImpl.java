package com.example.service.impl;

import com.example.entity.Component;
import com.example.entity.Device;
import com.example.entity.DeviceComponentSensor;
import com.example.entity.Sensor;
import com.example.mapper.DeviceComponentSensorMapper;
import com.example.service.DeviceComponentSensorService;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeviceComponentSensorServiceImpl extends BaseServiceImpl<DeviceComponentSensorMapper, DeviceComponentSensor> implements DeviceComponentSensorService {

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
        return buildByComponents(null, entityList);
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
        return buildBySensor(null, null, entityList);
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
            deviceComponentSensors.add(DeviceComponentSensor.builder()
                    .deviceId(deviceId)
                    .componentId(ComponentId)
                    .sensorId(sensorId)
                    .build());
        }
        return deviceComponentSensors;
    }

}
