package com.example.service;

import com.example.dto.DataLabelDTO;
import com.example.entity.DataLabel;

import java.util.List;

public interface DataLabelService extends BaseService<DataLabel> {
    void preAddList(List<DataLabelDTO> dtoList);
}
