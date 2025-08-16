package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.convert.BaseConvert;
import com.example.dto.BaseDTO;
import com.example.entity.BaseEntity;
import com.example.enums.ResponseMessageEnum;
import com.example.groups.Add;
import com.example.groups.Update;
import com.example.response.Response;
import com.example.vo.BaseVO;
import com.example.wrapper.NotNollLambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

@Validated
@Data
@AllArgsConstructor
public class BaseController<T extends BaseEntity, D extends BaseDTO, V extends BaseVO, S extends IService<T>, C extends BaseConvert<T, D, V>> {

    protected final S service;
    protected final C convert;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Response<Long> add(@RequestBody @Validated(Add.class) D dto) {
        dto.setId(null);
        preAdd(dto);
        T entity = convert.dtoToEntity(dto);
        service.save(entity);
        postAdd(entity, dto);
        return Response.success(entity.getId());
    }

    /**
     * 根据传入条件获取列表
     */
    @GetMapping("/query/list")
    public Response<List<V>> list(D dto) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = new NotNollLambdaQueryWrapper<>(service.getEntityClass());
        lambdaQueryWrapper.eq(T::getId, dto.getId());
        preList(dto, lambdaQueryWrapper);
        return Response.success(convert.entityToVo(service.list(lambdaQueryWrapper)));
    }

    /**
     * 分页获取
     */
    @GetMapping("/query/page/list")
    public Response<Page<V>> pageList(D dto) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = buildBaseQueryWrapper(dto);
        prePageList(dto, lambdaQueryWrapper);
        Page<T> page = service.page(Page.of(dto.getPageNum(), dto.getPageSize()), lambdaQueryWrapper);
        return Response.success(convert.entityToVo(page));
    }

    /**
     * 获取详情
     */
    @GetMapping("/query/{id}")
    public Response<V> get(@PathVariable Serializable id) {
        T entity = service.getById(id);
        if (entity == null) {
            return Response.fail("数据不存在");
        }
        return Response.success(convert.entityToVo(entity));
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Response<Void> update(@RequestBody @Validated(Update.class) D dto) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = new NotNollLambdaQueryWrapper<>(service.getEntityClass());
        lambdaQueryWrapper.eq(T::getId, dto.getId());
        if (!service.exists(lambdaQueryWrapper)) {
            return Response.fail(ResponseMessageEnum.ID_NOT_EXIST.getMessage());
        }
        preUpdate(dto);
        T entity = convert.dtoToEntity(dto);
        service.updateById(entity);
        postUpdate(entity, dto);
        return Response.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Response<Void> delete(@PathVariable Serializable id) {
        if (!service.removeById(id)) {
            return Response.fail("删除失败，数据不存在");
        }
        return Response.success();
    }

    public LambdaQueryWrapper<T> buildBaseQueryWrapper(D dto) {
        return new LambdaQueryWrapper<>(service.getEntityClass())
                .eq(T::getId, dto.getId())
                .eq(T::getDeleted, dto.getDeleted())
                .eq(T::getVersion, dto.getVersion())
                .eq(T::getCreateUser, dto.getCreateUser())
                .eq(T::getUpdateUser, dto.getUpdateUser());
    }

    /**
     * 新增前处理（子类可重写）
     */
    protected void preAdd(D dto) {
        // 子类可重写实现业务逻辑
    }

    /**
     * 新增后处理（子类可重写）
     */
    protected void postAdd(T entity, D dto) {
        // 子类可重写实现业务逻辑
    }

    /**
     * 分页查询前处理（子类可重写）
     */
    protected void preList(D dto, LambdaQueryWrapper<T> queryWrapper) {
        // 子类可重写实现自定义查询条件
    }

    /**
     * 分页查询前处理（子类可重写）
     */
    protected void prePageList(D dto, LambdaQueryWrapper<T> queryWrapper) {
        // 子类可重写实现自定义查询条件
    }

    /**
     * 更新前处理（子类可重写）
     */
    protected void preUpdate(D dto) {
        // 子类可重写实现业务逻辑
    }

    /**
     * 更新后处理（子类可重写）
     */
    protected void postUpdate(T entity, D dto) {
        // 子类可重写实现业务逻辑
    }
}
