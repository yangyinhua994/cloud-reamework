package com.example.service.impl;

import com.example.convert.ApiInfoConvert;
import com.example.dto.ApiInfoDTO;
import com.example.entity.ApiInfo;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.ApiInfoMapper;
import com.example.service.ApiInfoService;
import com.example.utils.CollectionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ApiInfoServiceImpl extends BaseServiceImpl<ApiInfoMapper, ApiInfo> implements ApiInfoService {

    private final ApiInfoConvert apiInfoConvert;

    @Override
    public List<ApiInfo> preAddList(List<ApiInfoDTO> dtoList) {

        if (CollectionUtils.isEmpty(dtoList)) {
            ApiException.error(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        List<ApiInfo> list = list();
        dtoList.removeIf(dto -> {
            for (ApiInfo entity : list) {
                if (Objects.equals(dto.getClassApiDesc(), entity.getClassApiDesc()) &&
                        Objects.equals(dto.getInterfaceApiDesc(), entity.getInterfaceApiDesc())) {
                    return true;
                }
            }
            return false;
        });
        return apiInfoConvert.dtoToEntity(dtoList);
    }
}
