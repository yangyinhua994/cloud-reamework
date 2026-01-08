package com.example.controller;

import com.example.annotation.LoginAdmin;
import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.groups.Login;
import com.example.response.Response;
import com.example.service.LoginService;
import com.example.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/user")
@Validated
public class LoginController extends BaseController<User, UserDTO, UserVO, LoginService, UserConvert> {

    public LoginController(LoginService service, UserConvert convert) {
        super(service, convert);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    @LoginAdmin()
    public Response<UserVO> login(@RequestBody @Validated(Login.class) UserDTO dto) {
        return Response.success(service.login(dto));
    }

}
