package com.example.controller;

import com.example.client.FinanceClient;
import com.example.constant.GlobalTransactionalConstant;
import com.example.constant.RedisConstant;
import com.example.convert.UserConvert;
import com.example.dto.FinanceUserDTO;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.enums.ResponseMessageEnum;
import com.example.groups.Login;
import com.example.response.Response;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.ObjectUtils;
import com.example.utils.PasswordUtil;
import com.example.utils.RedisUtil;
import com.example.vo.FinanceUserVO;
import com.example.vo.UserVO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.enums.ResponseMessageEnum.PHONE_OR_PASSWORD_ERROR;

/**
 * 用户登录接口
 */
@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserConvert userConvert;
    private final FinanceClient financeClient;
    private final RedisUtil redisUtil;

    @RequestMapping("/test")
    public Response<UserVO> test(String name) {
        return Response.success();
    }

    /**
     * 用户登录
     */
    // @SentinelResource(value = "/user/login", blockHandler = "loginBlockHandler", fallback = "loginFallbackHandler")
    @PostMapping("/login")
    public Response<UserVO> login(@RequestBody @Validated(Login.class) UserDTO dto) {
        User user = (User) redisUtil.get(RedisConstant.User.USER_PHONE, dto.getPhone());
        if (ObjectUtils.isEmpty(user)) {
            user = userService.getByPhone(dto.getPhone());
            if (ObjectUtils.isEmpty(user)) {
                Response.error(ResponseMessageEnum.USER_NOT_EXIST);
            }
            redisUtil.set(RedisConstant.User.USER_PHONE, dto.getPhone(), user);
        }

        if (!PasswordUtil.verifyPassword(dto.getPassword(), user.getPassword())) {
            return Response.fail(PHONE_OR_PASSWORD_ERROR);
        }
        String token = jwtUtil.generateToken(user);
        UserVO userVO = userConvert.entityToVo(user);
        userVO.setToken(token);
        return Response.success(userVO);
    }

    /**
     * 注册
     */
    @GlobalTransactional(name = GlobalTransactionalConstant.FinanceService.REGISTER, rollbackFor = Exception.class)
    @PostMapping("/register")
    public Response<UserVO> register(@RequestBody @Validated(Login.class) UserDTO dto) {
        boolean exists = userService.existsByPhone(dto.getPhone());
        if (exists) {
            return Response.fail(ResponseMessageEnum.PHONE_IS_REGISTERED);
        }
        dto.setPassword(PasswordUtil.encryptPassword(dto.getPassword()));
        userService.save(userConvert.dtoToEntity(dto));
        // 新建财务账户
        FinanceUserDTO financeUserDTO = FinanceUserDTO.builder()
                .phone(dto.getPhone())
                .username(dto.getUsername())
                .password(dto.getPassword()).build();
        Response<FinanceUserVO> response = financeClient.register(financeUserDTO);
        if (response.isFail()) {
            Response.error(response.getMessage());
        }
        return Response.success();
    }

    // 限流/熔断处理
    public Response<UserVO> loginBlockHandler(UserDTO dto, Throwable e) {
        return Response.fail("登录接口限流/熔断");
    }

    public Response<UserVO> loginFallbackHandler(UserDTO dto, Throwable e) {
        return Response.fail("登录接口降级");
    }

}
