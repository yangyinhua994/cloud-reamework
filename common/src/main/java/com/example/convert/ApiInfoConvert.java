package com.example.convert;

import com.example.dto.ApiInfoDTO;
import com.example.dto.UserDTO;
import com.example.entity.ApiInfo;
import com.example.entity.User;
import com.example.vo.ApiInfoVO;
import com.example.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApiInfoConvert extends BaseConvert<ApiInfo, ApiInfoDTO, ApiInfoVO> {
}
