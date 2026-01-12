package com.example.wrapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.example.utils.ObjectUtils;

public class NotNollLambdaQueryWrapper<T> extends LambdaQueryWrapper<T> {

    public NotNollLambdaQueryWrapper(Class<T> entityClass) {
        super(entityClass);
    }

    @Override
    public LambdaQueryWrapper<T> eq(SFunction<T, ?> column, Object val) {
        return super.eq(ObjectUtils.isNotEmpty(val), column, val);
    }

    @Override
    public LambdaQueryWrapper<T> like(SFunction<T, ?> column, Object val) {
        return super.like(ObjectUtils.isNotEmpty(val), column, val);
    }

}
