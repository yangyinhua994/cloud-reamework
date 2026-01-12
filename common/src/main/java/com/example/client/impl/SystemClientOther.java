package com.example.client.impl;

import com.example.client.SystemClient;
import com.example.dto.ApiInfoDTO;
import com.example.dto.UserApiInfoDTO;
import com.example.response.Response;
import com.example.vo.UserApiInfoVO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "system-service", fallback = SystemClientFallback.class)
@ConditionalOnProperty(name = "app.service.system", havingValue = "false", matchIfMissing = true)
public interface SystemClientOther extends SystemClient {

    @Override
    @PostMapping("/system/api/info/add/list")
    Response<Void> addList(List<ApiInfoDTO> dtoList);

    @Override
    @GetMapping("/system/user/api/info/query/list")
    Response<List<UserApiInfoVO>> list(UserApiInfoDTO dto);

}
