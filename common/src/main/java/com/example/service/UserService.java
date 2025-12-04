package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;
import com.example.response.Response;
import com.example.vo.OrderVO;

public interface UserService extends IService<User> {
    Response<OrderVO> getOrderById(Long id);

    User getByPhone(String phone);

    User getByPhone(String phone, boolean throwEx);

    boolean existsByPhone(String phone);
}
