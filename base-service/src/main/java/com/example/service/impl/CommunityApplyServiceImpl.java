package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.CommunityApply;
import com.example.mapper.CommunityApplyMapper;
import com.example.service.CommunityApplyService;
import org.springframework.stereotype.Service;

@Service
public class CommunityApplyServiceImpl extends ServiceImpl<CommunityApplyMapper, CommunityApply> implements CommunityApplyService {
}
