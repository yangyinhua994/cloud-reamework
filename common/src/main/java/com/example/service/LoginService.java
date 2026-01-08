package com.example.service;

import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.vo.UserVO;

public interface LoginService extends BaseService<User> {
    UserVO login(UserDTO dto);
}
