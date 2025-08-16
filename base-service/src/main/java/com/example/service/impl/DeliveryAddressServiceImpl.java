package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.DeliveryAddress;
import com.example.mapper.DeliveryAddressMapper;
import com.example.service.DeliveryAddressService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAddressServiceImpl extends ServiceImpl<DeliveryAddressMapper, DeliveryAddress> implements DeliveryAddressService {
}
