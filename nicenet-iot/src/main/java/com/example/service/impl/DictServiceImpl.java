package com.example.service.impl;

import com.example.entity.Dict;
import com.example.mapper.DictMapper;
import com.example.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements DictService {

    private final Map<Long, String> dictMap = new HashMap<>();

    @Override
    public void updateDictMap() {
        List<Dict> dictList = this.list();
        for (Dict dict : dictList) {
            dictMap.put(dict.getId(), dict.getDictValue());
        }
    }

    @Override
    public String getDictValue(Long id) {
        return dictMap.get(id);
    }

}
