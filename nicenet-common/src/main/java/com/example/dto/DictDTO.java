package com.example.dto;

import com.example.groups.Add;
import com.example.groups.AddUnit;
import com.example.groups.UpdateById;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DictDTO extends BaseDTO {
    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空", groups = {Add.class, UpdateById.class, AddUnit.class})
    private String dictValue;

    /**
     * 是否启用 1：是 其它：否
     */
    private Integer status;
}
