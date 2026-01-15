package com.example.service;

import com.example.dto.SensorParamDTO;
import com.example.entity.SensorParam;

import java.util.List;

public interface SensorParamService extends BaseService<SensorParam> {

    void preAddList(List<SensorParamDTO> dtoList);
}
