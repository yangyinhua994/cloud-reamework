package com.example.listener;

import com.example.broadcast.DatabaseChangesBroadcast;
import com.example.callback.DatabaseChangesCallback;
import com.example.constant.RedisConstant;
import com.example.entity.BaseEntity;
import com.example.entity.User;
import com.example.utils.RedisUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserChangesListener extends DatabaseChangesCallback<User> {

    private final DatabaseChangesBroadcast<User> broadcast;
    private final RedisUtil redisUtil;

    @PostConstruct
    @Override
    public void addCallback() {
        broadcast.addCallback(this);
    }

    @Override
    public void onChanged(User entity) {
        redisUtil.delete(RedisConstant.User.USER_ID, entity.getId());
    }

    @Override
    public String getTableName() {
        return "t_user";
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
