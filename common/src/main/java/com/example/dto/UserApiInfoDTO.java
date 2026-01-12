package com.example.dto;

import com.example.groups.Add;
import com.example.groups.Delete;
import com.example.groups.UpdateById;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserApiInfoDTO extends BaseDTO {
    @NotNull(message = "用户ID不能为空", groups = {Add.class, UpdateById.class, Delete.class})
    private Long userId;
    @NotBlank(message = "接口地址不能为空", groups = {Add.class, UpdateById.class})
    private String apiUrl;
}
