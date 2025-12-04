package com.example.convert;

import com.example.dto.FinanceUserDTO;
import com.example.entity.FinanceUser;
import com.example.vo.FinanceUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FinanceUserConvert extends BaseConvert<FinanceUser, FinanceUserDTO, FinanceUserVO> {
}
