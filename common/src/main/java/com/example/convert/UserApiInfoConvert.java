package com.example.convert;

import com.example.dto.UserApiInfoDTO;
import com.example.entity.UserApiInfo;
import com.example.vo.UserApiInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserApiInfoConvert extends BaseConvert<UserApiInfo, UserApiInfoDTO, UserApiInfoVO> {
}
