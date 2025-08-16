package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Community;
import com.example.mapper.CommunityMapper;
import com.example.service.CommunityService;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {
}
