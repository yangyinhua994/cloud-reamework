package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.ApiDesc;
import com.example.convert.ComponentConvert;
import com.example.dto.ComponentDTO;
import com.example.entity.Component;
import com.example.response.Response;
import com.example.service.ComponentService;
import com.example.vo.ComponentVO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    protected List<ComponentVO> listData(ComponentDTO dto) {
        return super.getService().listData(dto);
    }

    @Override
    protected Page<ComponentVO> pageData(ComponentDTO dto) {
        return super.getService().pageData(dto);
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

    /**
     * 下载部件导入excel模板
     */
    @GetMapping("/excel/dowload")
    @ApiDesc("下载部件导入excel模板")
    public void excelDownload(HttpServletResponse servlet) {
        super.getService().excelDownload(servlet);
    }

    /**
     * excel导入部件信息
     */
    @PostMapping("/excel/import")
    @ApiDesc("excel导入部件信息")
    public void importComponent(MultipartFile file) {
        super.getService().importComponent(file);
    }

}
