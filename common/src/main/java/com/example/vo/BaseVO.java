package com.example.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.time.LocalDate;

@Data
public class BaseVO {

    @TableId
    private Long id;
    private LocalDate createTime;
    private String createUser;
    private LocalDate updateTime;
    private String updateUser;
    private Integer deleted;
    private Integer version;

}
