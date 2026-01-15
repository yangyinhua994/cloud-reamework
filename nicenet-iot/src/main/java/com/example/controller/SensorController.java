package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.ApiDesc;
import com.example.convert.SensorConvert;
import com.example.dto.SensorDTO;
import com.example.entity.Sensor;
import com.example.response.Response;
import com.example.service.SensorService;
import com.example.vo.SensorVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 传感器管理接口
 */
@RestController
@RequestMapping("/iot/sensor")
@Validated
@ApiDesc("传感器管理接口")
public class SensorController extends BaseController<Sensor, SensorDTO, SensorVO, SensorService, SensorConvert> {
    public SensorController(SensorService service, SensorConvert convert) {
        super(service, convert);
    }

    @Override
    protected List<Sensor> preAddList(List<SensorDTO> dtoList) {
        return super.getService().preAddList(dtoList);
    }

    /*@Override
    public Response<List<SensorVO>> list(SensorDTO dto) {
        return Response.success(super.getService().list(dto));
    }

    @Override
    public Response<Page<SensorVO>> page(SensorDTO dto) {
        return Response.success(super.getService().page(dto));
    }*/
}
