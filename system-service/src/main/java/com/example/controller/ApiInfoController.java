package com.example.controller;

import com.example.convert.ApiInfoConvert;
import com.example.dto.ApiInfoDTO;
import com.example.entity.ApiInfo;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.service.ApiInfoService;
import com.example.utils.CollectionUtils;
import com.example.vo.ApiInfoVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * api数据接口
 */
@RestController
@RequestMapping("/system/api/info")
public class ApiInfoController extends BaseController<ApiInfo, ApiInfoDTO, ApiInfoVO, ApiInfoService, ApiInfoConvert> {

    public ApiInfoController(ApiInfoService service, ApiInfoConvert convert) {
        super(service, convert);
    }

    @Override
    protected List<ApiInfo> preAddList(List<ApiInfoDTO> dtoList) {
        return super.getService().preAddList(dtoList);
    }
}
