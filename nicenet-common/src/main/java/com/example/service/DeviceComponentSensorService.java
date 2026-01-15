package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.ComponentDTO;
import com.example.dto.DeviceComponentSensorDTO;
import com.example.dto.DeviceDTO;
import com.example.dto.SensorDTO;
import com.example.entity.Component;
import com.example.entity.Device;
import com.example.entity.DeviceComponentSensor;
import com.example.entity.Sensor;
import com.example.vo.DeviceComponentSensorVO;

import java.util.List;

public interface DeviceComponentSensorService extends BaseService<DeviceComponentSensor> {

    void addByDevice(Device entity);

    void addByDevice(List<Device> entityList);

    void addByComponent(Component entity);

    void addByComponent(List<Component> entityList);

    void addBySensor(Sensor entity);

    void addBySensor(List<Sensor> entityList);

    List<DeviceComponentSensor> buildByDevices(List<Device> entityList);

    List<DeviceComponentSensor> buildByComponents(List<Component> entityList);

    List<DeviceComponentSensor> buildByComponents(Long deviceId, List<Component> entityList);

    List<DeviceComponentSensor> buildBySensor(List<Sensor> entityList);

    List<DeviceComponentSensor> buildBySensor(Long deviceId, Long ComponentId, List<Sensor> entityList);

    DeviceComponentSensor build(Long deviceId, Long ComponentId, Long sensorId, Long sensorParamId);

    void removeBySensor(SensorDTO sensorDTO);

    void removeByDevice(DeviceDTO deviceDTO);

    void removeByComponent(ComponentDTO componentDTO);

    void removeBySensorId(Long sensorId);

    void removeBySensorParamId(Long sensorParamId);

    void remove(Long deviceId, Long componentId, Long sensorId, Long sensorParamId);

    List<DeviceComponentSensorVO> list(DeviceComponentSensorDTO dto);

    Page<DeviceComponentSensorVO> page(DeviceComponentSensorDTO dto);
}
