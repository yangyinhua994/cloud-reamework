package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.UserMapper;
import com.example.service.impl.BaseServiceImpl;
import com.example.utils.CollectionUtils;
import com.example.utils.JwtUtil;
import com.example.utils.PasswordUtil;
import com.example.vo.UserVO;
import com.example.wrapper.NotNollLambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl extends BaseServiceImpl<UserMapper, User> implements LoginService {

    private final UserConvert userConvert;
    private final JwtUtil jwtUtil;

    @Override
    public UserVO login(UserDTO dto) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new NotNollLambdaQueryWrapper<>(User.class)
                .eq(User::getUsername, dto.getUsername());
        List<User> list = list(lambdaQueryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            ApiException.error(ResponseMessageEnum.USER_NOT_EXIST);
        }
        if (list.size() > 1) {
            ApiException.error(ResponseMessageEnum.USERNAME_REPEAT);
        }
        User user = list.get(0);
        String password = user.getPassword();
        if (!PasswordUtil.verifyPassword(dto.getPassword(), password)) {
            ApiException.error(ResponseMessageEnum.ACCOUNT_OR_PASSWORD_ERROR);
        }
        UserVO userVO = userConvert.entityToVo(user);
        String token = jwtUtil.generateToken(user);
        userVO.setToken(token);
        userVO.setRefreshToken(jwtUtil.generateRefreshToken(user));
        return userVO;

    }

}
