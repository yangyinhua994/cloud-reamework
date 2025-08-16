package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.PickupAddress;
import com.example.mapper.PickupAddressMapper;
import com.example.service.PickupAddressService;
import org.springframework.stereotype.Service;

@Service
public class PickupAddressServiceImpl extends ServiceImpl<PickupAddressMapper, PickupAddress> implements PickupAddressService {
}
