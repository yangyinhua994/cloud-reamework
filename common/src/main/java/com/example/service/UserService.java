package com.example.service;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.vo.UserVO;

public interface UserService extends BaseService<User> {
    UserVO login(UserDTO dto);

    UserVO register(UserDTO dto);

    UserVO refreshToken(UserDTO dto);
}
