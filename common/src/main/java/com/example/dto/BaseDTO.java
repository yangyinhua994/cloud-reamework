package com.example.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.example.groups.Delete;
import com.example.groups.Update;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
public class BaseDTO {

    @TableId
    @NotNull(message = "ID不能为空", groups = {Update.class, Delete.class})
    private Long id;
    private LocalDate createTime;
    private String createUser;
    private LocalDate updateTime;
    private String updateUser;
    private Integer deleted;
    private Integer version;

    private Integer pageNum = 1;
    private Integer pageSize = 10;

}
