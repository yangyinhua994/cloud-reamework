package com.example.client;

import com.example.dto.ApiInfoDTO;
import com.example.dto.UserApiInfoDTO;
import com.example.response.Response;
import com.example.vo.UserApiInfoVO;

import java.util.List;

public interface SystemClient {

    Response<Void> addList(List<ApiInfoDTO> dtoList);

    Response<List<UserApiInfoVO>> list(UserApiInfoDTO dto);

}
