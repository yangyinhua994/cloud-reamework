package com.example.service.impl;

import com.example.dto.SensorParamDTO;
import com.example.entity.SensorParam;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.SensorParamMapper;
import com.example.service.DictService;
import com.example.service.SensorParamService;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import com.example.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorParamServiceImpl extends BaseServiceImpl<SensorParamMapper, SensorParam> implements SensorParamService {

    private final DictService dictService;

    @Override
    public void preAddList(List<SensorParamDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)){
            return;
        }
        for (SensorParamDTO sensorParamDTO : dtoList) {
            Long unitId = sensorParamDTO.getUnitId();
            if (ObjectUtils.isNotEmpty(unitId) && StringUtils.isBlank(dictService.getDictValue(unitId))){
                ApiException.error(ResponseMessageEnum.UNIT_ID_ERROR);
            }
            Long dataTypeId = sensorParamDTO.getDataTypeId();
            if (ObjectUtils.isNotEmpty(dataTypeId) && StringUtils.isBlank(dictService.getDictValue(dataTypeId))){
                ApiException.error(ResponseMessageEnum.DATA_TYPE_ID_ERROR);
            }
        }

    }
}
