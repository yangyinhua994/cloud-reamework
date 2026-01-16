package com.example.controller;

import com.example.annotation.ApiDesc;
import com.example.convert.DeviceComponentSensorConvert;
import com.example.dto.DeviceComponentSensorDTO;
import com.example.entity.DeviceComponentSensor;
import com.example.response.Response;
import com.example.service.DeviceComponentSensorService;
import com.example.vo.DeviceComponentSensorVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备、部件、传感器管理接口
 */
@RestController
@RequestMapping("/iot/device/component/sensor")
@Validated
@ApiDesc("部件管理接口")
public class DeviceComponentSensorController extends BaseController<DeviceComponentSensor, DeviceComponentSensorDTO, DeviceComponentSensorVO, DeviceComponentSensorService, DeviceComponentSensorConvert> {
    public DeviceComponentSensorController(DeviceComponentSensorService service, DeviceComponentSensorConvert convert) {
        super(service, convert);
    }

    public Response<Void> bind(DeviceComponentSensorDTO dto) {
        return super.getService().bind(dto);
    }

}
