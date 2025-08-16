package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.BaseEntity;
import com.example.mapper.BaseMapper;
import com.example.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BaseServiceImpl extends ServiceImpl<BaseMapper<BaseEntity>, BaseEntity> implements BaseService<BaseEntity> {
}
