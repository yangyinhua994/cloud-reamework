package com.example.service;

import com.example.dto.UserApiInfoDTO;
import com.example.entity.UserApiInfo;

import java.util.List;

public interface UserApiInfoService extends BaseService<UserApiInfo> {

    List<UserApiInfo> preAddList(List<UserApiInfoDTO> dtoList);

    List<UserApiInfo> getByApiUrls(List<String> apiUrls);

    Boolean isExist(List<String> apiUrls);


}
