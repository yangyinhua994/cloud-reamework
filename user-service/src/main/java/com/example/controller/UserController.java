package com.example.controller;

import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理接口
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController extends BaseController<User, UserDTO, UserVO, UserService, UserConvert> {

    public UserController(UserService service, UserConvert convert) {
        super(service, convert);
    }
}
