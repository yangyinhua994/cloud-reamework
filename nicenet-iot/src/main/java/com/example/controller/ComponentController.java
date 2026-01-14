package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.ApiDesc;
import com.example.convert.ComponentConvert;
import com.example.dto.ComponentDTO;
import com.example.entity.Component;
import com.example.response.Response;
import com.example.service.ComponentService;
import com.example.vo.ComponentVO;
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
    protected List<Component> preAddList(List<ComponentDTO> dtoList) {
        return super.getService().preAddList(dtoList);
    }

    @Override
    protected void postAddList(List<Component> components) {
        super.getService().postAddList( components);
    }

    @Override
    public Response<List<ComponentVO>> list(ComponentDTO dto) {
        return Response.success(super.getService().list(dto));
    }

    @Override
    public Response<Page<ComponentVO>> page(ComponentDTO dto) {
        return Response.success(super.getService().page(dto));
    }
}
