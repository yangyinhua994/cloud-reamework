package com.example.enums;

import lombok.Getter;

@Getter
public enum DictTypeEnum {

    PHYSICAL_UNIT("physical_unit", "物理单位"),
    DATA_TYPE("data_type", "数据类型");

    private final String code;
    private final String desc;

    DictTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
