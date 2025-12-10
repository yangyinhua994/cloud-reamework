package com.example.controller;

import com.example.constant.RedisConstant;
import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.enums.DeleteEnum;
import com.example.enums.ResponseMessageEnum;
import com.example.response.Response;
import com.example.service.UserService;
import com.example.utils.RedisUtil;
import com.example.utils.StringUtils;
import com.example.utils.ObjectUtils;
import com.example.utils.PasswordUtil;
import com.example.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理接口
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController extends BaseController<User, UserDTO, UserVO, UserService, UserConvert> {

    private final RedisUtil redisUtil;

    public UserController(UserService service, UserConvert convert, RedisUtil redisUtil) {
        super(service, convert);
        this.redisUtil = redisUtil;
    }

    @Override
    protected User preAdd(UserDTO dto) {
        User user = super.service.getByPhone(dto.getPhone());
        if (ObjectUtils.isNotEmpty(user)) {
            if (DeleteEnum.isDeleted(user.getDeleted())) {
                user.setDeleted(DeleteEnum.NOT_DELETED.getCode());
                return user;
            }
            Response.error(ResponseMessageEnum.PHONE_IS_REGISTERED);
        }
        dto.setPassword(PasswordUtil.encryptPassword(dto.getPassword()));
        return super.getConvert().dtoToEntity(dto);
    }

    @Override
    protected void preUpdate(UserDTO dto) {
        if (dto != null) {
            String password = dto.getPassword();
            if (StringUtils.isNotBlank(password)) {
                dto.setPassword(PasswordUtil.encryptPassword(password));
            }
        }
    }

    @Override
    protected void postUpdate(User entity) {
        // 更新后置，将数据保存到缓存中
        redisUtil.set(RedisConstant.User.USER_ID, entity.getId(), entity);
    }

    @Override
    protected User preGet(Long id) {
        // 查询前置，先查询缓存是否存在该数据
        return (User) redisUtil.get(RedisConstant.User.USER_ID, id);
    }

    @Override
    protected void postGet(User entity) {
        // 查询后置，将数据保存到缓存中
        redisUtil.set(RedisConstant.User.USER_ID, entity.getId(), entity);
    }

    @Override
    protected void postDelete(Long id) {
        // 删除后置，将数据从缓存中删除
        redisUtil.delete(RedisConstant.User.USER_ID, id);
    }

}
