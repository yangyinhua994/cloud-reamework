package com.example.vo;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DictVO extends BaseVO {
    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典类型
     */
    private String dictCode;

    /**
     * 字典值
     */
    private String dictValue;

    /**
     * 是否启用 1：是 其它：否
     */
    private Integer status;
}
