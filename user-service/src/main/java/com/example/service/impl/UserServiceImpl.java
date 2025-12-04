package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.client.OrderClient;
import com.example.entity.User;
import com.example.enums.ResponseMessageEnum;
import com.example.mapper.UserMapper;
import com.example.response.Response;
import com.example.service.UserService;
import com.example.util.StringUtils;
import com.example.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final OrderClient orderClient;

    @Override
    public Response<OrderVO> getOrderById(Long id) {
        return orderClient.getOrderById(id);
    }

    @Override
    public User getByPhone(String phone) {
        return getByPhone(phone, true);
    }

    @Override
    public User getByPhone(String phone, boolean throwEx) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone);
        return getOne(queryWrapper, throwEx);
    }

    @Override
    public boolean existsByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            Response.error(ResponseMessageEnum.PHONE_NOT_EXIST);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone);
        return exists(queryWrapper);
    }
}
