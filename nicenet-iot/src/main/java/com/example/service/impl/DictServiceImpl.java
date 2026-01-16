package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.convert.DictConvert;
import com.example.dto.DictDTO;
import com.example.entity.Dict;
import com.example.enums.DictTypeEnum;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.DictMapper;
import com.example.service.DictService;
import com.example.utils.ObjectUtils;
import com.example.utils.StringUtils;
import com.example.vo.DictVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements DictService {

    private final Map<Long, String> dictMap = new HashMap<>();
    private final DictConvert dictConvert;

    @Override
    public void updateDictMap() {
        List<Dict> dictList = this.list();
        for (Dict dict : dictList) {
            dictMap.put(dict.getId(), dict.getDictValue());
        }
    }

    @Override
    public String getDictValue(Long id) {
        return dictMap.get(id);
    }

    @Override
    public void preList(DictDTO dto, LambdaQueryWrapper<Dict> queryWrapper) {
        queryWrapper.eq(Dict::getDictType, dto.getDictType())
                .eq(Dict::getDictCode, dto.getDictCode())
                .eq(Dict::getDictValue, dto.getDictValue())
                .eq(Dict::getStatus, dto.getStatus());
    }

    @Override
    public Long addIfNotExist(String unit) {
        if (StringUtils.isBlank(unit)) {
            log.warn("单位不能为空");
            ApiException.error(ResponseMessageEnum.PARAM_ERROR);
        }
        DictVO dictVO = getByDictValue(unit);
        if (dictVO != null) {
            return dictVO.getId();
        }
        DictDTO dictDTO = DictDTO.builder()
                .dictValue(unit)
                .dictCode(DictTypeEnum.PHYSICAL_UNIT.getCode())
                .build();
        dictVO = add(dictDTO);
        return dictVO.getId();
    }

    @Override
    public DictVO add(DictDTO dictDTO) {
        if (ObjectUtils.isEmpty(dictDTO)) {
            ApiException.error(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        Dict entity = dictConvert.dtoToEntity(dictDTO);
        save(entity);
        return dictConvert.entityToVo(entity);
    }

    @Override
    public DictVO getByDictValue(String dictValue) {
        if (StringUtils.isBlank(dictValue)) {
            return null;
        }
        LambdaQueryWrapper<Dict> queryWrapper = new LambdaQueryWrapper<Dict>()
                .eq(Dict::getDictValue, dictValue);
        return dictConvert.entityToVo(getOne(queryWrapper));
    }

}
