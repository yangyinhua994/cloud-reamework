package com.example.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.controller.SensorParamController;
import com.example.dto.SensorDTO;
import com.example.dto.SensorParamDTO;
import com.example.entity.SensorParam;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.SensorParamMapper;
import com.example.service.DeviceComponentSensorService;
import com.example.service.DictService;
import com.example.service.SensorParamService;
import com.example.service.SensorService;
import com.example.utils.CollectionUtils;
import com.example.utils.ExcelUtils;
import com.example.utils.ObjectUtils;
import com.example.utils.StringUtils;
import com.example.vo.SensorParamVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorParamServiceImpl extends BaseServiceImpl<SensorParamMapper, SensorParam> implements SensorParamService {

    private final DictService dictService;
    private final SensorService sensorService;
    // 因为Controller层会有自身特定的校验逻辑和生命周期，所以不然绕过controller层
    @Lazy
    @Resource
    private SensorParamController sensorParamController;
    private final DeviceComponentSensorService deviceComponentSensorService;
    private final SensorParamMapper sensorParamMapper;

    @Override
    public void preAddList(List<SensorParamDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        for (SensorParamDTO sensorParamDTO : dtoList) {
            Long unitId = sensorParamDTO.getUnitId();
            if (ObjectUtils.isNotEmpty(unitId) && StringUtils.isBlank(dictService.getDictValue(unitId))) {
                ApiException.error(ResponseMessageEnum.UNIT_ID_ERROR);
            }
            Long dataTypeId = sensorParamDTO.getDataTypeId();
            if (ObjectUtils.isNotEmpty(dataTypeId) && StringUtils.isBlank(dictService.getDictValue(dataTypeId))) {
                ApiException.error(ResponseMessageEnum.DATA_TYPE_ID_ERROR);
            }
            List<SensorDTO> sensorDTOList = sensorParamDTO.getSensorDTOList();
            sensorService.checkSensorIds(sensorDTOList);
        }

    }

    @Override
    public void postAddList(List<SensorParam> sensorParams) {
        if (CollectionUtils.isEmpty(sensorParams)) {
            return;
        }
        deviceComponentSensorService.addBySensorParams(sensorParams);
    }

    @Override
    public void excelDownload(HttpServletResponse servlet) {
        ExcelUtils.downloadSensorParamExcelTemplate(servlet);
    }

    @Override
    public void importComponent(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            ApiException.error(ResponseMessageEnum.FILE_NOT_EXIST);
        }
        List<SensorParamDTO> sensorParamDTOS = ExcelUtils.readExcel(file, SensorParamDTO.class);
        if (CollectionUtils.isEmpty(sensorParamDTOS)) {
            return;
        }
        for (SensorParamDTO sensorParamDTO : sensorParamDTOS) {
            String unit = sensorParamDTO.getUnit();
            if (StringUtils.isNotBlank(unit)) {
                Long unitId = dictService.addIfNotExist(unit);
                sensorParamDTO.setUnitId(unitId);
            }

            String dataType = sensorParamDTO.getDataType();
            if (StringUtils.isNotBlank(dataType)) {
                Long dataTypeId = dictService.addIfNotExist(dataType);
                sensorParamDTO.setDataTypeId(dataTypeId);
            }
        }
        sensorParamController.addList(sensorParamDTOS);
    }

    @Override
    public void preUpdate(SensorParamDTO dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return;
        }
        deviceComponentSensorService.removeBySensorParam(dto);
    }

    @Override
    public void postUpdate(SensorParam entity) {
        if (ObjectUtils.isEmpty(entity)) {
            return;
        }
        deviceComponentSensorService.addBySensorParam(entity);
    }

    @Override
    public List<SensorParamVO> listData(SensorParamDTO dto) {
        return sensorParamMapper.listData(dto);
    }

    @Override
    public Page<SensorParamVO> pageData(SensorParamDTO dto) {
        return sensorParamMapper.pageData(Page.of(dto.getPageNum(), dto.getPageSize()), dto);
    }

    @Override
    public void preReturn(List<SensorParamVO> sensorParamVOS) {
        if (CollectionUtils.isEmpty(sensorParamVOS)) {
            return;
        }
        for (SensorParamVO sensorParamVO : sensorParamVOS) {
            Long unitId = sensorParamVO.getUnitId();
            if (ObjectUtils.isNotEmpty(unitId)) {
                sensorParamVO.setUnit(dictService.getDictValue(unitId));
            }
            Long dataTypeId = sensorParamVO.getDataTypeId();
            if (ObjectUtils.isNotEmpty(dataTypeId)) {
                sensorParamVO.setDataType(dictService.getDictValue(dataTypeId));
            }
        }
    }
}
