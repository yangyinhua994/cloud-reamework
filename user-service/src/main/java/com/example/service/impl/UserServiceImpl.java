package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.client.OrderClient;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.response.Response;
import com.example.service.UserService;
import com.example.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final OrderClient orderClient;

    @Override
    public Response<OrderVO> getOrderById(Long id) {
        return orderClient.getOrderById(id);
    }
}
