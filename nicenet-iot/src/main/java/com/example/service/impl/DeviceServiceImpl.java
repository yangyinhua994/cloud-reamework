package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.ComponentDTO;
import com.example.dto.DeviceDTO;
import com.example.entity.Device;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.DeviceMapper;
import com.example.service.ComponentService;
import com.example.service.DeviceComponentSensorService;
import com.example.service.DeviceService;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import com.example.vo.DeviceVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl extends BaseServiceImpl<DeviceMapper, Device> implements DeviceService {

    private final @Lazy ComponentService componentService;
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
    public void preAddList(List<DeviceDTO> dtoList) {

        List<String> deviceNumbers = dtoList.stream().map(DeviceDTO::getDeviceNumber).filter(ObjectUtils::isNotEmpty).toList();
        List<Device> devices = this.getByDeviceNumbers(deviceNumbers);
        if (CollectionUtils.isNotEmpty(devices)) {
            String deviceNumber = devices.get(0).getDeviceNumber();
            ApiException.error("设备编号重复: " + deviceNumber);
        }

        List<List<ComponentDTO>> componentDTOListS = dtoList.stream().map(DeviceDTO::getComponentDTOList).filter(ObjectUtils::isNotEmpty).toList();
        if (CollectionUtils.isNotEmpty(componentDTOListS)) {
            componentDTOListS.forEach(componentService::checkComponentIds);
        }
    }

    @Override
    public List<DeviceVO> listData(DeviceDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return List.of();
        }
        return deviceMapper.listData(dto);
    }

    @Override
    public Page<DeviceVO> pageData(DeviceDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return Page.of(1, 10);
        }
        IPage<DeviceVO> page = Page.of(dto.getPageNum(), dto.getPageSize());
        return deviceMapper.pageData(page, dto);
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

    @Override
    public void checkIdByDTOList(List<DeviceDTO> deviceDTOList) {
        if (CollectionUtils.isEmpty(deviceDTOList)) {
            return;
        }
        List<Long> ids = deviceDTOList.stream().map(DeviceDTO::getId).filter(ObjectUtils::isNotEmpty).toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        this.checkIds(ids);
    }

    @Override
    public void postUpdate(Device entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        deviceComponentSensorService.addByDevice(entity);
    }

    @Override
    public void preUpdate(DeviceDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return;
        }
        deviceComponentSensorService.removeByDevice(dto);
    }

    @Override
    public void preReturn(List<DeviceVO> deviceVOS) {
        if (CollectionUtils.isEmpty(deviceVOS)) {
            return;
        }
        deviceVOS.forEach(deviceVO -> {
            componentService.preReturn(deviceVO.getComponentVOList());
        });
    }

}
