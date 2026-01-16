package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    protected void preAddList(List<SensorDTO> dtoList) {
        super.getService().preAddList(dtoList);
    }

    @Override
    protected void postAddList(List<Sensor> sensors) {
        super.getService().postAddList(sensors);
    }

    @Override
    protected void preList(SensorDTO dto, LambdaQueryWrapper<Sensor> queryWrapper) {
        super.getService().preList(dto, queryWrapper);
    }

    @Override
    protected void prePageList(SensorDTO dto, LambdaQueryWrapper<Sensor> queryWrapper) {
        preList(dto, queryWrapper);
    }

    @Override
    protected List<SensorVO> listData(SensorDTO dto) {
        return super.getService().listData(dto);
    }

    @Override
    public Response<Page<SensorVO>> page(SensorDTO dto) {
        return Response.success(super.getService().pageData(dto));
    }

    @Override
    protected void preUpdate(SensorDTO dto) {
        super.getService().preUpdate(dto);
    }

    @Override
    protected void postUpdate(Sensor entity) {
        super.getService().postUpdate(entity);
    }

    @Override
    protected void postDelete(Long id) {
        super.getService().postDelete(id);
    }

    @Override
    protected void preReturn(List<SensorVO> sensorVOList) {
        super.getService().preReturn(sensorVOList);
    }

}
