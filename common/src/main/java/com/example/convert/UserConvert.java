package com.example.convert;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserConvert extends BaseConvert<User, UserDTO, UserVO> {
}
