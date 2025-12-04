package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.FinanceUser;

public interface FinanceUserService extends IService<FinanceUser> {
    FinanceUser getByPhone(String phone);

    FinanceUser getByPhone(String phone, Integer deleted);

    boolean existsByPhone(String phone);
}
