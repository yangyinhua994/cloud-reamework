package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.enums.ResponseMessageEnum;
import com.example.exception.ApiException;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.utils.PasswordUtil;
import com.example.vo.UserVO;
import com.example.wrapper.NotNollLambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    private final UserConvert userConvert;
    private final JwtUtil jwtUtil;

    @Override
    public UserVO login(UserDTO dto) {
        List<User> list = getByUserName(dto.getUsername());
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
        userVO.setToken(jwtUtil.generateToken(user));
        userVO.setRefreshToken(jwtUtil.generateRefreshToken(user));
        return userVO;

    }

    @Override
    public UserVO register(UserDTO dto) {
        List<User> list = getByUserName(dto.getUsername());
        if (!list.isEmpty()) {
            ApiException.error(ResponseMessageEnum.USER_EXIST);
        }
        User user = userConvert.dtoToEntity(dto);
        user.setPassword(PasswordUtil.encryptPassword(dto.getPassword()));
        save(user);
        return userConvert.entityToVo(user);
    }

    public List<User> getByUserName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new NotNollLambdaQueryWrapper<>(User.class)
                .eq(User::getUsername, username);
        return list(lambdaQueryWrapper);
    }

}
