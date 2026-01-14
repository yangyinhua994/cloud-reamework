package com.example.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.ComponentDTO;
import com.example.entity.Component;
import com.example.vo.ComponentVO;

import java.util.List;

public interface ComponentMapper extends BaseMapper<Component> {
    List<ComponentVO> list(ComponentDTO dto);

    Page<ComponentVO> page(Page<Object> of, ComponentDTO dto);
}
