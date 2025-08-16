package com.example.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.util.ObjectUtils;

public class NotNollLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {
    @Override
    public LambdaQueryWrapper<T> eq(SFunction<T, ?> column, Object val) {
        if (ObjectUtils.isEmpty(val)) {
            return this;
        } else {
            return super.eq(column, val);
        }
    }

    @Override
    public LambdaQueryWrapper<T> like(SFunction<T, ?> column, Object val) {
        if (ObjectUtils.isEmpty(val)) {
            return this;
        } else {
            return super.like(column, val);
        }
    }

    public NotNollLambdaQueryWrapper(Class<T> entityClass) {
        super(entityClass);
    }
}
