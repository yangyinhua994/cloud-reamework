package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.*;
import com.example.entity.*;
import com.example.response.Response;
import com.example.vo.DeviceComponentSensorVO;

import java.util.List;

public interface DeviceComponentSensorService extends BaseService<DeviceComponentSensor> {

    void addByDevice(Device entity);

    void addByDevice(List<Device> entityList);

    void addByComponent(Component entity);

    void addByComponent(List<Component> entityList);

    void addBySensor(Sensor entity);

    void addBySensor(List<Sensor> entityList);

    void addBySensorParam(SensorParam entity);

    void addBySensorParams(List<SensorParam> entityList);

    List<DeviceComponentSensor> buildBySensorParams(List<SensorParam> entityList);

    List<DeviceComponentSensor> buildByDevices(List<Device> entityList);

    List<DeviceComponentSensor> buildByComponents(List<Component> entityList);

    List<DeviceComponentSensor> buildByComponents(Long deviceId, List<Component> entityList);

    List<DeviceComponentSensor> buildBySensor(List<Sensor> entityList);

    List<DeviceComponentSensor> buildBySensor(Long deviceId, Long ComponentId, List<Sensor> entityList);

    DeviceComponentSensor build(Long deviceId, Long ComponentId, Long sensorId, Long sensorParamId);

    void removeBySensor(SensorDTO sensorDTO);

    void removeBySensorParam(SensorParamDTO sensorDTO);

    void removeByDevice(DeviceDTO deviceDTO);

    void removeByComponent(ComponentDTO componentDTO);

    void removeBySensorId(Long sensorId);

    void removeBySensorParamId(Long sensorParamId);

    void remove(Long deviceId, Long componentId, Long sensorId, Long sensorParamId);

    List<DeviceComponentSensorVO> listData(DeviceComponentSensorDTO dto);

    Page<DeviceComponentSensorVO> pageData(DeviceComponentSensorDTO dto);

    void checkIds(List<Long> ids);

    Response<Void> bind(DeviceComponentSensorDTO dto);
}
