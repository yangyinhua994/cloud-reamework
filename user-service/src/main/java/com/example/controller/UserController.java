package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.annotation.LoginAdmin;
import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.groups.Login;
import com.example.groups.Register;
import com.example.response.Response;
import com.example.service.UserService;
import com.example.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController extends BaseController<User, UserDTO, UserVO, UserService, UserConvert> {

    public UserController(UserService service, UserConvert convert) {
        super(service, convert);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Response<UserVO> login(@RequestBody @Validated(Login.class) UserDTO dto) {
        return Response.success(service.login(dto));
    }

    /**
     * 注册
     */
    @GetMapping("/register")
    public Response<UserVO> register(@RequestBody @Validated(Register.class) UserDTO dto) {
        return Response.success(service.register(dto));
    }

    @Override
    @LoginAdmin
    public Response<Page<UserVO>> page(UserDTO dto) {
        return super.page(dto);
    }
}
