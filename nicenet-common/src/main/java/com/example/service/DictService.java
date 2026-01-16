package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dto.DictDTO;
import com.example.entity.Dict;
import com.example.response.Response;
import com.example.vo.DictVO;

import java.util.List;

public interface DictService extends BaseService<Dict> {
    void updateDictMap();

    String getDictValue(Long id);

    void preList(DictDTO dto, LambdaQueryWrapper<Dict> queryWrapper);

    Long addIfNotExist(String unit);

    DictVO add(DictDTO dictDTO);

    DictVO getByDictValue(String dictValue);
}
