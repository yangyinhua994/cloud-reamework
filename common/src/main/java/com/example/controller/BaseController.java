package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.annotation.ApiDesc;
import com.example.convert.BaseConvert;
import com.example.dto.BaseDTO;
import com.example.entity.BaseEntity;
import com.example.enums.DeleteEnum;
import com.example.enums.ResponseMessageEnum;
import com.example.groups.Add;
import com.example.groups.UpdateById;
import com.example.response.Response;
import com.example.utils.CollectionUtils;
import com.example.vo.BaseVO;
import com.example.wrapper.NotNollLambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Data
@AllArgsConstructor
public class BaseController<T extends BaseEntity, D extends BaseDTO, V extends BaseVO, S extends IService<T>, C extends BaseConvert<T, D, V>> {

    protected final S service;
    protected final C convert;

    /**
     * 新增数据
     */
    @ApiDesc("新增数据")
    @PostMapping("/add")
    public Response<V> add(@RequestBody @Validated(Add.class) D dto) {
        dto.setId(null);
        T entity = preAdd(dto);
        adds(List.of(entity));
        postAdd(entity, dto);
        return Response.success(convert.entityToVo(entity));
    }

    /**
     * 新增数据列表
     */
    @ApiDesc("新增数据列表")
    @PostMapping("/add/list")
    public Response<Void> addList(@RequestBody @Validated(Add.class) List<D> dtoList) {
        List<T> ts = preAddList(dtoList);
        adds(ts);
        return Response.success();
    }

    /**
     * 根据传入条件获取列表
     */
    @ApiDesc("获取数据列表")
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
    @ApiDesc("分页获取数据")
    @GetMapping("/query/page")
    public Response<Page<V>> page(D dto) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = buildBaseQueryWrapper(dto);
        prePageList(dto, lambdaQueryWrapper);
        Page<T> page = service.page(Page.of(dto.getPageNum(), dto.getPageSize()), lambdaQueryWrapper);
        return Response.success(convert.entityToVo(page));
    }

    /**
     * 获取详情
     */
    @ApiDesc("获取数据详情")
    @GetMapping("/query/{id}")
    public Response<V> get(@PathVariable Long id) {
        if (id == null) {
            return Response.fail(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        T entity = preGet(id);
        if (entity != null) {
            return Response.success(convert.entityToVo(entity));
        }
        entity = service.getById(id);
        if (entity == null) {
            return Response.fail("数据不存在");
        }
        postGet(entity);
        return Response.success(convert.entityToVo(entity));
    }

    /**
     * 修改
     */
    @ApiDesc("修改数据")
    @PutMapping("/update")
    public Response<Void> update(@RequestBody @Validated(UpdateById.class) D dto) {
        LambdaQueryWrapper<T> lambdaQueryWrapper = new NotNollLambdaQueryWrapper<>(service.getEntityClass());
        lambdaQueryWrapper.eq(T::getId, dto.getId());
        if (!service.exists(lambdaQueryWrapper)) {
            return Response.fail(ResponseMessageEnum.ID_NOT_EXIST.getMessage());
        }
        preUpdate(dto);
        T entity = convert.dtoToEntity(dto);
        update(entity);
        // 暂时没想到能用到的地方，注释掉
        // postUpdate(service.getById(entity.getId()), dto);
        return Response.success();
    }

    /**
     * 逻辑删除
     */
    @ApiDesc("逻辑删除数据")
    @DeleteMapping("/delete/soft/{id}")
    public Response<Void> deleteSoft(@PathVariable Long id) {
        if (id == null) {
            return Response.fail(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        T t = getService().getById(id);
        if (t == null) {
            return Response.fail(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        t.setDeleted(DeleteEnum.DELETED.getCode());
        update(t);
        return Response.success();
    }

    /**
     * 物理删除
     */
    @ApiDesc("物理删除数据")
    @DeleteMapping("/delete/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        if (id == null) {
            return Response.fail(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        preDelete(id);
        if (!service.removeById(id)) {
            return Response.fail(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        postDelete(id);
        return Response.success();
    }

    public void adds(List<T> ts) {
        if (CollectionUtils.isEmpty(ts)) {
            return;
        }
        for (T t : ts) {
            if (t.getId() == null) {
                t.setId(IdWorker.getId());
            }
        }
        service.saveOrUpdateBatch(ts);
    }


    public Response<Void> update(T entity) {
        if (entity == null) {
            return Response.fail(ResponseMessageEnum.DATA_NOT_EXIST);
        }
        service.updateById(entity);
        postUpdate(entity);
        return Response.success();
    }

    public LambdaQueryWrapper<T> buildBaseQueryWrapper(D dto) {
        return new NotNollLambdaQueryWrapper<>(service.getEntityClass())
                .eq(T::getId, dto.getId())
                .eq(T::getDeleted, dto.getDeleted())
                .eq(T::getVersion, dto.getVersion())
                .eq(T::getCreateUser, dto.getCreateUser())
                .eq(T::getUpdateUser, dto.getUpdateUser());
    }

    /**
     * 新增前处理（子类可重写）
     */
    protected T preAdd(D dto) {
        return getConvert().dtoToEntity(dto);
    }


    protected List<T> preAddList(List<D> dtoList) {
        return this.convert.dtoToEntity(dtoList);
    }

    protected T preGet(Long id) {
        return null;
    }

    protected void preDelete(Long id) {
    }

    protected void postDelete(Long id) {
    }

    protected void postGet(T entity) {
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

    /**
     * 更新后处理（子类可重写）
     */
    protected void postUpdate(T entity) {
        // 子类可重写实现业务逻辑
    }

}
