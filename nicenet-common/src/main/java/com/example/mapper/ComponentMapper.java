package com.example.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.ComponentDTO;
import com.example.entity.Component;
import com.example.vo.ComponentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ComponentMapper extends BaseMapper<Component> {
    List<ComponentVO> list(@Param("dto") ComponentDTO dto);

    Page<ComponentVO> page(@Param("page") Page<Object> page, @Param("dto") ComponentDTO dto);
}
