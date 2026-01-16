package com.example.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.SensorParamDTO;
import com.example.entity.SensorParam;
import com.example.vo.SensorParamVO;

import java.util.List;

public interface SensorParamMapper extends BaseMapper<SensorParam> {

    List<SensorParamVO> listData(SensorParamDTO dto);

    Page<SensorParamVO> pageData(Page<Object> of, SensorParamDTO dto);
}
