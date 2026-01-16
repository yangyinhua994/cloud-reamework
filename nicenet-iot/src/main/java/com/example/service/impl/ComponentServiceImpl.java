package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.controller.ComponentController;
import com.example.dto.ComponentDTO;
import com.example.entity.Component;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.ComponentMapper;
import com.example.service.ComponentService;
import com.example.service.DeviceComponentSensorService;
import com.example.service.DeviceService;
import com.example.service.SensorService;
import com.example.utils.CollectionUtils;
import com.example.utils.ExcelUtils;
import com.example.utils.ObjectUtils;
import com.example.vo.ComponentVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl extends BaseServiceImpl<ComponentMapper, Component> implements ComponentService {

    private final SensorService sensorService;
    @Lazy
    @Resource
    private DeviceService deviceService;
    private final DeviceComponentSensorService deviceComponentSensorService;
    private final ComponentMapper componentMapper;
    // 因为Controller层会有自身特定的校验逻辑和生命周期，所以不然绕过controller层
    @Lazy
    @Resource
    private ComponentController componentController;

    @Override
    public boolean exists(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        LambdaQueryWrapper<Component> queryWrapper = new LambdaQueryWrapper<Component>()
                .in(Component::getId, ids);
        return super.count(queryWrapper) == ids.size();
    }

    @Override
    public void checkIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        if (!exists(ids)) {
            ApiException.error(ResponseMessageEnum.CONTENT_NOT_EXIST);
        }
    }

    @Override
    public void checkComponentIds(List<ComponentDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        checkIds(dtoList.stream().map(ComponentDTO::getId).filter(ObjectUtils::isNotEmpty).toList());

    }

    @Override
    public void checkComponentNumber(List<ComponentDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        List<String> componentNumbers = dtoList.stream().map(ComponentDTO::getComponentNumber).filter(ObjectUtils::isNotEmpty).toList();
        List<Component> components = getByComponentNumbers(componentNumbers);
        if (CollectionUtils.isNotEmpty(components)) {
            String componentNumber = components.get(0).getComponentNumber();
            ApiException.error("部件编号重复: " + componentNumber);
        }
    }

    @Override
    public List<Component> getByComponentNumbers(List<String> componentNumbers) {
        if (CollectionUtils.isEmpty(componentNumbers)) {
            return List.of();
        }
        LambdaQueryWrapper<Component> queryWrapper = new LambdaQueryWrapper<Component>()
                .in(Component::getComponentNumber, componentNumbers);
        return super.list(queryWrapper);
    }

    @Override
    public void preAddList(List<ComponentDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }

        checkComponentNumber(dtoList);
        checkComponentIds(dtoList);

        for (ComponentDTO componentDTO : dtoList) {
            sensorService.checkSensorIds(componentDTO.getSensorDTOList());
            deviceService.checkIdByDTOList(componentDTO.getDeviceDTOList());
        }

    }

    @Override
    public void postAddList(List<Component> components) {
        deviceComponentSensorService.addByComponent(components);
    }

    @Override
    public List<ComponentVO> listData(ComponentDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return List.of();
        }
        return componentMapper.listData(dto);
    }

    @Override
    public Page<ComponentVO> pageData(ComponentDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }
        return componentMapper.pageData(Page.of(dto.getPageNum(), dto.getPageSize()), dto);
    }

    @Override
    public void preUpdate(ComponentDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return;
        }
        deviceComponentSensorService.removeByComponent(dto);
    }

    @Override
    public void postUpdate(Component entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        deviceComponentSensorService.addByComponent(entity);
    }

    @Override
    public void preReturn(List<ComponentVO> componentVOS) {
        if (CollectionUtils.isEmpty(componentVOS)) {
            return;
        }
        componentVOS.forEach(componentVO -> {
            sensorService.preReturn(componentVO.getSensorVOList());
        });
    }

    @Override
    public void excelDownload(HttpServletResponse response) {
        ExcelUtils.downloadComponentExcelTemplate(response);
    }

    @Override
    public void importComponent(MultipartFile file) {
        if (ObjectUtils.isEmpty(file)) {
            ApiException.error(ResponseMessageEnum.FILE_NOT_EXIST);
        }
        List<ComponentDTO> componentDTOList = ExcelUtils.readExcel(file, ComponentDTO.class);
        if (CollectionUtils.isEmpty(componentDTOList)) {
            return;
        }
        componentController.addList(componentDTOList);
    }

    @Override
    public void preList(ComponentDTO dto, LambdaQueryWrapper<Component> queryWrapper) {
        queryWrapper.eq(Component::getComponentNumber, dto.getComponentNumber())
                .like(Component::getComponentName, dto.getComponentName());
    }
}
