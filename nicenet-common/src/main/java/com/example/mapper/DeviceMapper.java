package com.example.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.DeviceDTO;
import com.example.entity.Device;
import com.example.vo.DeviceVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper extends BaseMapper<Device> {
    List<DeviceVO> list(@Param("dto") DeviceDTO dto);

    Page<DeviceVO> page(IPage<DeviceVO> page, @Param("dto") DeviceDTO dto);
}
