package com.example.controller;

import com.example.constant.GlobalTransactionalConstant;
import com.example.convert.FinanceUserConvert;
import com.example.dto.FinanceUserDTO;
import com.example.entity.FinanceUser;
import com.example.enums.ResponseMessageEnum;
import com.example.groups.Register;
import com.example.response.Response;
import com.example.service.FinanceUserService;
import com.example.utils.StringUtils;
import com.example.utils.PasswordUtil;
import com.example.vo.FinanceUserVO;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 财务服务用户管理接口
 */
@RestController
@RequestMapping("/finance/user")
@Validated
public class FinanceUserController extends BaseController<FinanceUser, FinanceUserDTO, FinanceUserVO, FinanceUserService, FinanceUserConvert> {

    public FinanceUserController(FinanceUserService service, FinanceUserConvert convert) {
        super(service, convert);
    }

    /**
     * 注册
     */
    @GlobalTransactional(name = GlobalTransactionalConstant.FinanceService.REGISTER, rollbackFor = Exception.class)
    @PostMapping("/register")
    public Response<FinanceUserVO> register(@RequestBody @Validated(Register.class) FinanceUserDTO financeUserDTO) {
        boolean exists = super.getService().existsByPhone(financeUserDTO.getPhone());
        if (exists) {
            Response.error(ResponseMessageEnum.PHONE_IS_REGISTERED);
        }
        String password = financeUserDTO.getPassword();
        if (StringUtils.isNotBlank(password)) {
            financeUserDTO.setPassword(PasswordUtil.encryptPassword(password));
        }
        return super.add(financeUserDTO);
    }

}
