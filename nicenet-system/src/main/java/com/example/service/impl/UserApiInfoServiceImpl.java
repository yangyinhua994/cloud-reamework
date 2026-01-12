package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.convert.UserApiInfoConvert;
import com.example.dto.UserApiInfoDTO;
import com.example.entity.UserApiInfo;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.UserApiInfoMapper;
import com.example.service.UserApiInfoService;
import com.example.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserApiInfoServiceImpl extends BaseServiceImpl<UserApiInfoMapper, UserApiInfo> implements UserApiInfoService {

    private final UserApiInfoConvert userApiInfoConvert;

    @Override
    public List<UserApiInfo> preAddList(List<UserApiInfoDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return new ArrayList<>();
        }
        List<UserApiInfo> userApiInfos = userApiInfoConvert.dtoToEntity(dtoList);
        List<String> apiUrls = userApiInfos.stream().map(UserApiInfo::getApiUrl).toList();
        if (isExist(apiUrls)) {
            ApiException.error(ResponseMessageEnum.DATA_REPEAT);
        }
        return userApiInfos;

    }

    @Override
    public List<UserApiInfo> getByApiUrls(List<String> apiUrls) {
        if (CollectionUtils.isEmpty(apiUrls)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<UserApiInfo> lambdaQueryWrapper = new LambdaQueryWrapper<UserApiInfo>()
                .in(UserApiInfo::getApiUrl, apiUrls);
        return this.list(lambdaQueryWrapper);
    }

    @Override
    public Boolean isExist(List<String> apiUrls) {
        return CollectionUtils.isNotEmpty(getByApiUrls(apiUrls));
    }
}
