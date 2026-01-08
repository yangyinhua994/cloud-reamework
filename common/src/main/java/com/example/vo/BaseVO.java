package com.example.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseVO {

    @TableId
    private Long id;
    private LocalDateTime createTime;
    private String createUser;
    private LocalDateTime updateTime;
    private String updateUser;
    private Integer deleted;
    private Integer version;

}
