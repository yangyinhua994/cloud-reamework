package com.example.convert;

import com.example.dto.SysUserDTO;
import com.example.entity.SysUser;
import com.example.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysUserConvert extends BaseConvert<SysUser, SysUserDTO, SysUserVO> {

}
