package com.example.service;

import com.example.entity.Component;
import com.example.entity.Device;
import com.example.entity.DeviceComponentSensor;
import com.example.entity.Sensor;

import java.util.List;

public interface DeviceComponentSensorService extends BaseService<DeviceComponentSensor> {

    void addByDevice(List<Device> entityList);

    void addByComponent(List<Component> entityList);

    void addBySensor(List<Sensor> entityList);
}
