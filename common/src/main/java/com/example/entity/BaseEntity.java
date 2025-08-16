package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BaseEntity implements Serializable {

    @TableId
    private Long id;
    private LocalDate createTime;
    private String createUser;
    private LocalDate updateTime;
    private String updateUser;
    private Integer deleted;
    private Integer version;

}
