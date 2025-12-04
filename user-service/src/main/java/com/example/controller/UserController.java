package com.example.controller;

import com.example.convert.UserConvert;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.enums.DeleteEnum;
import com.example.enums.ResponseMessageEnum;
import com.example.response.Response;
import com.example.service.UserService;
import com.example.util.StringUtils;
import com.example.utils.ObjectUtils;
import com.example.utils.PasswordUtil;
import com.example.vo.OrderVO;
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

    public UserController(UserService service, UserConvert convert) {
        super(service, convert);
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
        User user = super.service.getByPhone(dto.getPhone());
        if (ObjectUtils.isNotEmpty(user)) {
            if (user.isDelete()) {
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

}
