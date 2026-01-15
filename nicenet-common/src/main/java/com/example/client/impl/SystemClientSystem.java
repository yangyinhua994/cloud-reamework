package com.example.client.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.client.SystemClient;
import com.example.convert.ApiInfoConvert;
import com.example.convert.UserApiInfoConvert;
import com.example.dto.ApiInfoDTO;
import com.example.dto.UserApiInfoDTO;
import com.example.entity.ApiInfo;
import com.example.entity.UserApiInfo;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.response.Response;
import com.example.service.ApiInfoService;
import com.example.service.UserApiInfoService;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import com.example.vo.UserApiInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.service.system", havingValue = "true")
@Component
public class SystemClientSystem implements SystemClient {

    private final ApiInfoService apiInfoService;
    private final UserApiInfoService userApiInfoService;
    private final UserApiInfoConvert userApiInfoConvert;
    private final ApiInfoConvert apiInfoConvert;

    public Response<Void> addList(List<ApiInfoDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            ApiException.error(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        apiInfoService.preAddList(dtoList);
        List<ApiInfo> apiInfos = apiInfoConvert.dtoToEntity(dtoList);
        apiInfoService.saveBatch(apiInfos);
        return Response.success();
    }

    public Response<List<UserApiInfoVO>> list(UserApiInfoDTO dto) {
        if (ObjectUtils.isEmpty(dto) || ObjectUtils.isEmpty(dto.getUserId()) || ObjectUtils.isEmpty(dto.getApiUrl())) {
            ApiException.error(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        LambdaQueryWrapper<UserApiInfo> lambdaQueryWrapper = new LambdaQueryWrapper<UserApiInfo>()
                .eq(UserApiInfo::getUserId, dto.getUserId())
                .eq(UserApiInfo::getApiUrl, dto.getApiUrl());
        List<UserApiInfo> list = userApiInfoService.list(lambdaQueryWrapper);
        return Response.success(userApiInfoConvert.entityToVo(list));
    }

}
