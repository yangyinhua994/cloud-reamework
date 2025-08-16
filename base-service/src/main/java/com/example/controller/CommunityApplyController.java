package com.example.controller;

import com.example.convert.CommunityApplyConvert;
import com.example.dto.CommunityApplyDTO;
import com.example.entity.CommunityApply;
import com.example.service.CommunityApplyService;
import com.example.vo.CommunityApplyVO;
import org.springframework.web.bind.annotation.*;

/**
 * 小区入驻申请管理接口
 */
@RestController
@RequestMapping("/base/community-apply")
public class CommunityApplyController extends BaseController<CommunityApply, CommunityApplyDTO, CommunityApplyVO, CommunityApplyService, CommunityApplyConvert> {

    public CommunityApplyController(CommunityApplyService service, CommunityApplyConvert convert) {
        super(service, convert);
    }
}
