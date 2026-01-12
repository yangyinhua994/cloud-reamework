package com.example.controller;

import com.example.annotation.ApiDesc;
import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.groups.Login;
import com.example.groups.RefreshToken;
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
@ApiDesc("用户接口")
public class UserController extends BaseController<User, UserDTO, UserVO, UserService, UserConvert> {

    public UserController(UserService service, UserConvert convert) {
        super(service, convert);
    }

    /**
     * 登录
     */
    @ApiDesc("用户登录")
    @PostMapping("/login")
    public Response<UserVO> login(@RequestBody @Validated(Login.class) UserDTO dto) {
        return Response.success(service.login(dto));
    }

    /**
     * 注册
     */
    @ApiDesc("用户注册")
    @GetMapping("/register")
    public Response<UserVO> register(@RequestBody @Validated(Register.class) UserDTO dto) {
        return Response.success(service.register(dto));
    }

    /**
     * 刷新token
     */
    @ApiDesc("刷新token")
    @PostMapping("/refreshToken")
    public Response<UserVO> refreshToken(@RequestBody @Validated(RefreshToken.class) UserDTO dto) {
        return Response.success(service.refreshToken(dto));
    }

}
