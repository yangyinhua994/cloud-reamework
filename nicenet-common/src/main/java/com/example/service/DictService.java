package com.example.service;

import com.example.entity.Dict;

public interface DictService extends BaseService<Dict> {
    void updateDictMap();

    String getDictValue(Long id);
}
