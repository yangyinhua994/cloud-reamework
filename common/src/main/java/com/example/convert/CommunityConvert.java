package com.example.convert;

import com.example.dto.CommunityDTO;
import com.example.entity.Community;
import com.example.vo.CommunityVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommunityConvert extends BaseConvert<Community, CommunityDTO, CommunityVO> {
}
