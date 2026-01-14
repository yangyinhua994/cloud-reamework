package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.DeviceDTO;
import com.example.entity.Device;
import com.example.vo.DeviceVO;

import java.util.List;

public interface DeviceService extends BaseService<Device> {
    List<Device> getByDeviceNumbers(List<String> deviceNumbers);

    List<Device> preAddList(List<DeviceDTO> dtoList);

    List<DeviceVO> list(DeviceDTO dto);

    Page<DeviceVO> page(DeviceDTO dto);

    void postAddList(List<Device> devices);

    boolean exists(List<Long> ids);

    void checkIds(List<Long> ids);
}
