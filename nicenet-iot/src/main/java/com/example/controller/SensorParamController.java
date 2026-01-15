package com.example.controller;

import com.example.annotation.ApiDesc;
import com.example.convert.SensorParamConvert;
import com.example.dto.SensorParamDTO;
import com.example.entity.SensorParam;
import com.example.service.SensorParamService;
import com.example.vo.SensorParamVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 传感器参数管理接口
 */
@RestController
@RequestMapping("/iot/sensor/param")
@Validated
@ApiDesc("传感器参数管理接口")
public class SensorParamController extends BaseController<SensorParam, SensorParamDTO, SensorParamVO, SensorParamService, SensorParamConvert> {

    public SensorParamController(SensorParamService service, SensorParamConvert convert) {
        super(service, convert);
    }

    @Override
    protected void preAddList(List<SensorParamDTO> dtoList) {
        super.getService().preAddList(dtoList);
    }
}
