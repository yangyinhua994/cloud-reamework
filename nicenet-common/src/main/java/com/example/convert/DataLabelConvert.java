package com.example.convert;

import com.example.dto.DataLabelDTO;
import com.example.entity.DataLabel;
import com.example.vo.DataLabelVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DataLabelConvert extends BaseConvert<DataLabel, DataLabelDTO, DataLabelVO> {
}
