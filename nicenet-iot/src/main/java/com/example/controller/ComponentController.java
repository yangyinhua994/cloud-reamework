package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.ApiDesc;
import com.example.convert.ComponentConvert;
import com.example.dto.ComponentDTO;
import com.example.entity.Component;
import com.example.response.Response;
import com.example.service.ComponentService;
import com.example.utils.CollectionUtils;
import com.example.vo.ComponentVO;
import com.example.vo.SensorVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部件管理接口
 */
@RestController
@RequestMapping("/iot/component")
@Validated
@ApiDesc("部件管理接口")
public class ComponentController extends BaseController<Component, ComponentDTO, ComponentVO, ComponentService, ComponentConvert> {

    public ComponentController(ComponentService service, ComponentConvert convert) {
        super(service, convert);
    }

    @Override
    protected void preAddList(List<ComponentDTO> dtoList) {
        super.getService().preAddList(dtoList);
    }

    @Override
    protected void postAddList(List<Component> components) {
        super.getService().postAddList(components);
    }

    @Override
    public Response<List<ComponentVO>> list(ComponentDTO dto) {
        return Response.success(super.getService().list(dto));
    }

    @Override
    public Response<Page<ComponentVO>> page(ComponentDTO dto) {
        return Response.success(super.getService().page(dto));
    }

    @Override
    protected void preUpdate(ComponentDTO dto) {
        super.getService().preUpdate(dto);
    }

    @Override
    protected void postUpdate(Component entity) {
        super.getService().postUpdate(entity);
    }

    @Override
    protected void preReturn(List<ComponentVO> componentVOS) {
        super.getService().preReturn(componentVOS);
    }
}
