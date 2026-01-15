package com.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.ComponentDTO;
import com.example.entity.Component;
import com.example.vo.ComponentVO;

import java.util.List;

public interface ComponentService extends BaseService<Component> {
    boolean exists(List<Long> ids);

    void checkIds(List<Long> ids);

    void checkComponentIds(List<ComponentDTO> dtoList);

    void checkComponentNumber(List<ComponentDTO> dtoList);

    List<Component> getByComponentNumbers(List<String> componentNumbers);

    void preAddList(List<ComponentDTO> dtoList);

    void postAddList(List<Component> components);

    List<ComponentVO> list(ComponentDTO dto);

    Page<ComponentVO> page(ComponentDTO dto);

    void preUpdate(ComponentDTO dto);

    void postUpdate(Component entity);

    void preReturn(List<ComponentVO> componentVOS);
}
