package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.controller.DeviceComponentSensorController;
import com.example.convert.DeviceConvert;
import com.example.dto.ComponentDTO;
import com.example.dto.DeviceComponentSensorDTO;
import com.example.dto.DeviceDTO;
import com.example.dto.SensorDTO;
import com.example.entity.Device;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.DeviceMapper;
import com.example.service.ComponentService;
import com.example.service.DeviceComponentSensorService;
import com.example.service.DeviceService;
import com.example.service.SensorService;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import com.example.vo.DeviceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl extends BaseServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Lazy
    @Autowired
    private ComponentService componentService;
    @Lazy
    @Autowired
    private SensorService sensorService;
    private final DeviceConvert deviceConvert;
    private final DeviceMapper deviceMapper;
    private final DeviceComponentSensorService deviceComponentSensorService;

    @Override
    public List<Device> getByDeviceNumbers(List<String> deviceNumbers) {
        if (CollectionUtils.isEmpty(deviceNumbers)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<Device>()
                .in(Device::getDeviceNumber, deviceNumbers);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<Device> preAddList(List<DeviceDTO> dtoList) {

        List<String> deviceNumbers = dtoList.stream().map(DeviceDTO::getDeviceNumber).filter(ObjectUtils::isNotEmpty).toList();
        List<Device> devices = this.getByDeviceNumbers(deviceNumbers);
        if (CollectionUtils.isNotEmpty(devices)) {
            String deviceNumber = devices.get(0).getDeviceNumber();
            ApiException.error("设备编号重复: " + deviceNumber);
        }

        List<ComponentDTO> componentDTOS = dtoList.stream().map(DeviceDTO::getComponentDTO).filter(ObjectUtils::isNotEmpty).toList();
        if (CollectionUtils.isNotEmpty(componentDTOS)) {
            List<Long> componentIds = componentDTOS.stream().map(ComponentDTO::getId).filter(ObjectUtils::isNotEmpty).toList();
            componentService.checkIds(componentIds);
        }
        List<SensorDTO> sensorDTOS = dtoList.stream().map(DeviceDTO::getSensorDTO).filter(ObjectUtils::isNotEmpty).toList();
        if (CollectionUtils.isNotEmpty(sensorDTOS)) {
            List<Long> sensorIds = sensorDTOS.stream().map(SensorDTO::getId).filter(ObjectUtils::isNotEmpty).toList();
            sensorService.checkIds(sensorIds);
        }
        return deviceConvert.dtoToEntity(dtoList);
    }

    @Override
    public List<DeviceVO> list(DeviceDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return List.of();
        }
        return deviceMapper.list(dto);
    }

    @Override
    public Page<DeviceVO> page(DeviceDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return Page.of(1, 10);
        }
        IPage<DeviceVO> page = Page.of(dto.getPageNum(), dto.getPageSize());
        return deviceMapper.page(page, dto);
    }

    @Override
    public void postAddList(List<Device> devices) {
        deviceComponentSensorService.addByDevice(devices);

    }

    @Override
    public boolean exists(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<Device>()
                .in(Device::getId, ids);
        return super.count(queryWrapper) == ids.size();
    }

    @Override
    public void checkIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        if (!this.exists(ids)) {
            ApiException.error(ResponseMessageEnum.DEVICE_NOT_EXIST);
        }
    }

}
