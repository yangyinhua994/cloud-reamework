package com.example.client.impl;

import com.example.client.SystemClient;
import com.example.dto.ApiInfoDTO;
import com.example.dto.UserApiInfoDTO;
import com.example.response.Response;
import com.example.vo.UserApiInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

@Slf4j
public class SystemClientFallback implements FallbackFactory<SystemClient> {

    @Override
    public SystemClient create(Throwable cause) {
        return new SystemClient() {
            @Override
            public Response<Void> addList(List<ApiInfoDTO> dtoList) {
                log.warn("上报接口信息到系统服务失败，请检查系统服务是否运行", cause);
                return Response.fail();
            }

            @Override
            public Response<List<UserApiInfoVO>> list(UserApiInfoDTO dto) {
                log.warn("获取用户访问地址权限管理失败，请检查系统服务是否运行", cause);
                return Response.fail();
            }
        };
    }
}
