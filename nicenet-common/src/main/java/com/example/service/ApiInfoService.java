package com.example.service;

import com.example.dto.ApiInfoDTO;
import com.example.entity.ApiInfo;

import java.util.List;

public interface ApiInfoService extends BaseService<ApiInfo> {
    List<ApiInfo> preAddList(List<ApiInfoDTO> dtoList);
}
