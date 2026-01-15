package com.example.convert;

import com.example.dto.DictDTO;
import com.example.entity.Dict;
import com.example.vo.DictVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DictConvert extends BaseConvert<Dict, DictDTO, DictVO> {
}

