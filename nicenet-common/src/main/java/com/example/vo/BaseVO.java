package com.example.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseVO {

    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private LocalDateTime createTime;
    private String createUser;
    private LocalDateTime updateTime;
    private String updateUser;
    private Integer deleted;
    private Integer version;

    protected void preReturn() {
    }

}
