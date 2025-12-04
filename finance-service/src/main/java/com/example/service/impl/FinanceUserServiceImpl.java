package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.FinanceUser;
import com.example.mapper.FinanceUserMapper;
import com.example.service.FinanceUserService;
import com.example.wrapper.NotNollLambdaQueryWrapper;
import org.springframework.stereotype.Service;

@Service
public class FinanceUserServiceImpl extends ServiceImpl<FinanceUserMapper, FinanceUser> implements FinanceUserService {
    @Override
    public FinanceUser getByPhone(String phone) {
        return getByPhone(phone, null);
    }

    @Override
    public FinanceUser getByPhone(String phone, Integer deleted) {
        LambdaQueryWrapper<FinanceUser> queryWrapper = new NotNollLambdaQueryWrapper<>(FinanceUser.class)
                .eq(FinanceUser::getPhone, phone)
                .eq(FinanceUser::getDeleted, deleted);
        return super.getOne(queryWrapper, false);

    }

    @Override
    public boolean existsByPhone(String phone) {
        LambdaQueryWrapper<FinanceUser> queryWrapper = new NotNollLambdaQueryWrapper<>(FinanceUser.class)
                .eq(FinanceUser::getPhone, phone);
        return super.exists(queryWrapper);
    }

}
