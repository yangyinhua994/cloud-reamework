package com.example.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.example.groups.Delete;
import com.example.groups.UpdateById;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO {

    @TableId
    @NotNull(message = "ID不能为空", groups = {UpdateById.class, Delete.class})
    private Long id;
    private List<Long> ids;
    private LocalDateTime createTime;
    private String createUser;
    private LocalDateTime updateTime;
    private String updateUser;
    private Integer deleted;
    private Integer version;

    private long pageNum = 1;
    private long pageSize = 10;

}
