package com.example.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convert.ComponentConvert;
import com.example.dto.ComponentDTO;
import com.example.dto.DeviceDTO;
import com.example.dto.SensorDTO;
import com.example.entity.Component;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.ComponentMapper;
import com.example.service.ComponentService;
import com.example.service.DeviceComponentSensorService;
import com.example.service.DeviceService;
import com.example.service.SensorService;
import com.example.utils.ObjectUtils;
import com.example.vo.ComponentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl extends BaseServiceImpl<ComponentMapper, Component> implements ComponentService {

    @Lazy
    @Autowired
    private  DeviceService deviceService;
    @Lazy
    @Autowired
    private  SensorService sensorService;
    private final DeviceComponentSensorService deviceComponentSensorService;
    private final ComponentConvert componentConvert;
    private final ComponentMapper componentMapper;

    @Override
    public boolean exists(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        LambdaQueryWrapper<Component> queryWrapper = new LambdaQueryWrapper<Component>()
                .in(Component::getId, ids);
        return super.count(queryWrapper) == ids.size();
    }

    @Override
    public void checkIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        if (!exists(ids)) {
            ApiException.error(ResponseMessageEnum.CONTENT_NOT_EXIST);
        }
    }

    @Override
    public List<Component> getByComponentNumbers(List<String> componentNumbers) {
        if (CollectionUtils.isEmpty(componentNumbers)) {
            return List.of();
        }
        LambdaQueryWrapper<Component> queryWrapper = new LambdaQueryWrapper<Component>()
                .in(Component::getComponentNumber, componentNumbers);
        return super.list(queryWrapper);
    }

    @Override
    public List<Component> preAddList(List<ComponentDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return List.of();
        }
        List<String> componentNumbers = dtoList.stream().map(ComponentDTO::getComponentNumber).filter(ObjectUtils::isNotEmpty).toList();
        List<Component> components = getByComponentNumbers(componentNumbers);
        if (CollectionUtils.isNotEmpty(components)) {
            String componentNumber = components.get(0).getComponentNumber();
            ApiException.error("部件编号重复: " + componentNumber);
        }

        List<DeviceDTO> deviceDTOS = dtoList.stream().map(ComponentDTO::getDeviceDTO).filter(ObjectUtils::isNotEmpty).toList();
        if (CollectionUtils.isNotEmpty(deviceDTOS)){
            deviceService.checkIds(deviceDTOS.stream().map(DeviceDTO::getId).filter(ObjectUtils::isNotEmpty).toList());
        }

        List<SensorDTO> sensorDTOS = dtoList.stream().map(ComponentDTO::getSensorDTO).filter(ObjectUtils::isNotEmpty).toList();
        if (CollectionUtils.isNotEmpty(sensorDTOS)){
            sensorService.checkIds(sensorDTOS.stream().map(SensorDTO::getId).filter(ObjectUtils::isNotEmpty).toList());
        }
        return componentConvert.dtoToEntity(dtoList);
    }

    @Override
    public void postAddList(List<Component> components) {
        deviceComponentSensorService.addByComponent(components);
    }

    @Override
    public List<ComponentVO> list(ComponentDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return List.of();
        }
        return componentMapper.list(dto);
    }

    @Override
    public Page<ComponentVO> page(ComponentDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }
        return componentMapper.page(Page.of(dto.getPageNum(), dto.getPageSize()), dto);
    }
}
