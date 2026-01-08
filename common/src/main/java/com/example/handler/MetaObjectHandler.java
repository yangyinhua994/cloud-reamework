package com.example.handler;

import com.example.entity.User;
import com.example.enums.DeleteEnum;
import com.example.holder.UserContextHolder;
import com.example.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "version", Integer.class, 1);
        this.strictInsertFill(metaObject, "deleted", Integer.class, DeleteEnum.NOT_DELETED.getCode());

        User user = UserContextHolder.getUser();
        String username = ObjectUtils.isEmpty(user) || user.getUsername() == null ? "未知用户" : user.getUsername();
        this.strictInsertFill(metaObject, "createUser", String.class, username);
        this.strictInsertFill(metaObject, "updateUser", String.class, username);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User user = UserContextHolder.getUser();
        String username = ObjectUtils.isEmpty(user) || user.getUsername() == null ? "未知用户" : user.getUsername();
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "updateUser", String.class, username);
    }

}

