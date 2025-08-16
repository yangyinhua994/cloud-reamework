package com.example.convert;

import com.example.dto.CommunityApplyDTO;
import com.example.entity.CommunityApply;
import com.example.vo.CommunityApplyVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommunityApplyConvert extends BaseConvert<CommunityApply, CommunityApplyDTO, CommunityApplyVO> {
}
