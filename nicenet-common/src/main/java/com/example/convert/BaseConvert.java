package com.example.convert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.BaseEntity;
import com.example.dto.BaseDTO;
import com.example.vo.BaseVO;

import java.util.List;

public interface BaseConvert<T extends BaseEntity, D extends BaseDTO, V extends BaseVO> {

    V entityToVo(T entity);

    List<V> entityToVo(List<T> entityList);

    Page<V> entityToVo(Page<T> entityPage);

    V dtoToVo(D dto);

    List<V> dtoToVo(List<D> dtoList);

    Page<V> dtoToVo(Page<D> dtoPage);

    T dtoToEntity(D dto);

    List<T> dtoToEntity(List<D> dtoList);

    Page<T> dtoToEntity(Page<D> dtoPage);

    T voToEntity(V vo);

    List<T> voToEntity(List<V> voList);

    Page<T> voToEntity(Page<V> voPage);

    D voToDto(V vo);

    List<D> voToDto(List<V> voList);

    Page<D> voToDto(Page<V> voPage);

    D entityToDto(T entity);

    List<D> entityToDto(List<T> entityList);

    Page<D> entityToDto(Page<T> entityPage);
}
