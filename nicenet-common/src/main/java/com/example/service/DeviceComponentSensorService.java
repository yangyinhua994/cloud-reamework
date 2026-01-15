package com.example.service;

import com.example.entity.Component;
import com.example.entity.Device;
import com.example.entity.DeviceComponentSensor;
import com.example.entity.Sensor;

import java.util.List;

public interface DeviceComponentSensorService extends BaseService<DeviceComponentSensor> {

    void addByDevice(List<Device> entityList);

    void addByComponent(List<Component> entityList);

    List<DeviceComponentSensor> buildByDevices(List<Device> entityList);

    List<DeviceComponentSensor> buildByComponents(List<Component> entityList);

    List<DeviceComponentSensor> buildByComponents(Long deviceId, List<Component> entityList);

    List<DeviceComponentSensor> buildBySensor(List<Sensor> entityList);

    List<DeviceComponentSensor> buildBySensor(Long deviceId, Long ComponentId, List<Sensor> entityList);
}
