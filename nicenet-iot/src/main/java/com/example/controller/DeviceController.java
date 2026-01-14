package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.ApiDesc;
import com.example.convert.DeviceConvert;
import com.example.dto.DeviceDTO;
import com.example.entity.Device;
import com.example.response.Response;
import com.example.service.DeviceService;
import com.example.vo.DeviceVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备管理接口
 */
@RestController
@RequestMapping("/iot/device")
@Validated
@ApiDesc("设备管理接口")
public class DeviceController extends BaseController<Device, DeviceDTO, DeviceVO, DeviceService, DeviceConvert> {
    public DeviceController(DeviceService service, DeviceConvert convert) {
        super(service, convert);
    }

    @Override
    protected List<Device> preAddList(List<DeviceDTO> dtoList) {
        return super.getService().preAddList(dtoList);
    }

    @Override
    protected void postAddList(List<Device> devices) {
        super.getService().postAddList(devices);
    }

    @Override
    public Response<List<DeviceVO>> list(DeviceDTO dto) {
        return Response.success(super.getService().list(dto));
    }

    @Override
    public Response<Page<DeviceVO>> page(DeviceDTO dto) {
        return Response.success(super.getService().page(dto));
    }
}
