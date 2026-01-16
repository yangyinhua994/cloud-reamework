package com.example.service;

import com.example.dto.DataLabelDTO;
import com.example.entity.DataLabel;
import com.example.mapper.DataLabelMapper;
import com.example.service.impl.BaseServiceImpl;
import com.example.utils.CollectionUtils;
import com.example.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataLabelServiceImpl extends BaseServiceImpl<DataLabelMapper, DataLabel> implements DataLabelService {

    private final DeviceComponentSensorService deviceComponentSensorService;

    @Override
    public void preAddList(List<DataLabelDTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            return;
        }
        List<Long> deviceComponentSensorIds = dtoList.stream().map(DataLabelDTO::getDeviceComponentSensorId).filter(ObjectUtils::isNotEmpty).toList();
        if (CollectionUtils.isEmpty(deviceComponentSensorIds)) {
            return;
        }
        deviceComponentSensorService.checkIds(deviceComponentSensorIds);
    }
}
