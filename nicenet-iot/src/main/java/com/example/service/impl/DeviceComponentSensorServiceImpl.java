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
        List<DeviceComponentSensor> list = new ArrayList<>();
        for (Device device : entityList) {
            Component component = device.getComponent();
            Sensor sensor = device.getSensor();
            if (ObjectUtils.isAllEmpty(component, sensor)) {
                continue;
            }

            DeviceComponentSensor deviceComponentSensor = new DeviceComponentSensor();
            deviceComponentSensor.setDeviceId(device.getId());
            if (ObjectUtils.isNotEmpty(component)) {
                deviceComponentSensor.setComponentId(component.getId());
            }
            if (ObjectUtils.isNotEmpty(sensor)) {
                deviceComponentSensor.setSensorId(sensor.getId());
            }
            list.add(deviceComponentSensor);
        }
        saveBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addByComponent(List<Component> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        List<DeviceComponentSensor> list = new ArrayList<>();
        for (Component component : entityList) {
            Device device = component.getDevice();
            Sensor sensor = component.getSensor();
            if (ObjectUtils.isAllEmpty(device, sensor)) {
                continue;
            }
            DeviceComponentSensor deviceComponentSensor = new DeviceComponentSensor();
            deviceComponentSensor.setComponentId(component.getId());
            if (ObjectUtils.isNotEmpty(device)) {
                deviceComponentSensor.setDeviceId(device.getId());
            }
            if (ObjectUtils.isNotEmpty(sensor)) {
                deviceComponentSensor.setSensorId(sensor.getId());
            }
            list.add(deviceComponentSensor);
        }
        saveBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBySensor(List<Sensor> entityList) {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        List<DeviceComponentSensor> list = new ArrayList<>();
        for (Sensor sensor : entityList) {
            Device device = sensor.getDevice();
            Component component = sensor.getComponent();
            if (ObjectUtils.isAllEmpty(device, component)) {
                continue;
            }
            DeviceComponentSensor deviceComponentSensor = new DeviceComponentSensor();
            deviceComponentSensor.setSensorId(sensor.getId());
            if (ObjectUtils.isNotEmpty(device)) {
                deviceComponentSensor.setDeviceId(device.getId());
            }
            if (ObjectUtils.isNotEmpty(component)) {
                deviceComponentSensor.setComponentId(component.getId());
            }
            list.add(deviceComponentSensor);
        }
        saveBatch(list);
    }
}
