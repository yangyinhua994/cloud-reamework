package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.DeviceDTO;
import com.example.entity.Device;
import com.example.vo.DeviceVO;

import java.util.List;

public interface DeviceService extends BaseService<Device> {
    List<Device> getByDeviceNumbers(List<String> deviceNumbers);

    void preAddList(List<DeviceDTO> dtoList);

    List<DeviceVO> listData(DeviceDTO dto);

    Page<DeviceVO> pageData(DeviceDTO dto);

    void postAddList(List<Device> devices);

    boolean exists(List<Long> ids);

    void checkIds(List<Long> ids);

    void checkIdByDTOList(List<DeviceDTO> deviceDTOList);

    void postUpdate(Device entity);

    void preUpdate(DeviceDTO dto);

    void preReturn(List<DeviceVO> deviceVOS);
}
