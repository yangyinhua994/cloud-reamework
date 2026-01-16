package com.example.controller;

import com.example.annotation.ApiDesc;
import com.example.convert.UserApiInfoConvert;
import com.example.dto.UserApiInfoDTO;
import com.example.entity.UserApiInfo;
import com.example.service.UserApiInfoService;
import com.example.vo.UserApiInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户接口访问控制接口
 */
@ApiDesc("用户接口访问控制接口")
@RestController
@RequestMapping("/system/user/api/info")
public class UserApiInfoController extends BaseController<UserApiInfo, UserApiInfoDTO, UserApiInfoVO, UserApiInfoService, UserApiInfoConvert> {

    public UserApiInfoController(UserApiInfoService service, UserApiInfoConvert convert) {
        super(service, convert);
    }

    /**
     * 获取当前用户可访问的接口
     */
    @GetMapping("/getUserApiInfo")
    public List<UserApiInfoVO> getUserApiInfo() {
        return List.of();
    }
}
