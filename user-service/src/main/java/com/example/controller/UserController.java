package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.enums.DeleteEnum;
import com.example.enums.ResponseMessageEnum;
import com.example.groups.Login;
import com.example.response.Response;
import com.example.service.UserService;
import com.example.util.StringUtils;
import com.example.utils.JwtUtil;
import com.example.utils.ObjectUtils;
import com.example.utils.PasswordUtil;
import com.example.vo.OrderVO;
import com.example.vo.UserVO;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.enums.ResponseMessageEnum.PHONE_OR_PASSWORD_ERROR;

/**
 * 用户管理接口
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController extends BaseController<User, UserDTO, UserVO, UserService, UserConvert> {

    private final JwtUtil jwtUtil;

    public UserController(UserService service, UserConvert convert, JwtUtil jwtUtil) {
        super(service, convert);
        this.jwtUtil = jwtUtil;
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Response<UserVO> login(@RequestBody @Validated(Login.class) UserDTO dto) {
        User user = getByPhone(dto.getPhone());
        if (ObjectUtils.isEmpty(user)) {
            Response.error(ResponseMessageEnum.USER_NOT_EXIST);
        }
        if (!PasswordUtil.verifyPassword(dto.getPassword(), user.getPassword())) {
            return Response.fail(PHONE_OR_PASSWORD_ERROR);
        }
        String token = jwtUtil.generateToken(user);
        UserVO userVO = super.convert.entityToVo(user);
        userVO.setToken(token);
        return Response.success(userVO);
    }

    /**
     * 获取订单详情
     */
    @PostMapping("/order/query/{id}")
    public Response<OrderVO> getOrderById(@PathVariable("id") Long id) {
        return super.getService().getOrderById(id);
    }


    @Override
    protected User preAdd(UserDTO dto) {
        User user = getByPhone(dto.getPhone());
        if (ObjectUtils.isNotEmpty(user)) {
            if (user.isDelete()) {
                user.setDeleted(DeleteEnum.NOT_DELETED.getCode());
                return user;
            }
            Response.error(ResponseMessageEnum.PHONE_IS_EXIST);
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

    public User getByPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getPhone, phone);
        List<User> list = super.getService().list(queryWrapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

}
