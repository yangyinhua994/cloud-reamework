package com.example.service;

import com.example.dto.ApiInfoDTO;
import com.example.entity.ApiInfo;

import java.util.List;

public interface ApiInfoService extends BaseService<ApiInfo> {

    void preAddList(List<ApiInfoDTO> dtoList);

    List<ApiInfo> queryTreeByIds(Long Ids);

}
