package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.convert.CommunityConvert;
import com.example.dto.CommunityDTO;
import com.example.entity.Community;
import com.example.enums.ResponseMessageEnum;
import com.example.response.Response;
import com.example.service.CommunityService;
import com.example.vo.CommunityVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小区管理接口
 */
@RestController
@RequestMapping("/base/community")
public class CommunityController extends BaseController<Community, CommunityDTO, CommunityVO, CommunityService, CommunityConvert> {

    public CommunityController(CommunityService service, CommunityConvert convert) {
        super(service, convert);
    }

    @Override
    protected void preAdd(CommunityDTO dto) {
        // 检查小区名称是否已存在
        if (service.exists(new LambdaQueryWrapper<Community>().eq(Community::getName, dto.getName()))) {
            Response.error(ResponseMessageEnum.COMMUNITY_NAME_EXIST);
        }
    }

    @Override
    protected void preList(CommunityDTO dto, LambdaQueryWrapper<Community> queryWrapper) {
        queryWrapper.like(Community::getName, dto.getName())
                .eq(Community::getAddress, dto.getAddress())
                .eq(Community::getContactPerson, dto.getContactPerson())
                .eq(Community::getContactPhone, dto.getContactPhone())
                .eq(Community::getStatus, dto.getStatus());
    }
}
