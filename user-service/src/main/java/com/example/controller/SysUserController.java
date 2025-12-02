package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.convert.SysUserConvert;
import com.example.dto.SysUserDTO;
import com.example.entity.BaseEntity;
import com.example.entity.SysUser;
import com.example.enums.ResponseMessageEnum;
import com.example.response.Response;
import com.example.service.SysUserService;
import com.example.vo.SysUserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户管理接口
 */
@RestController
@RequestMapping("/user/sys-user")
@Validated
public class SysUserController extends BaseController<SysUser, SysUserDTO, SysUserVO, SysUserService, SysUserConvert> {

    public SysUserController(SysUserService service, SysUserConvert convert) {
        super(service, convert);
    }

    @Override
    protected SysUser preAdd(SysUserDTO dto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, dto.getUsername());
        if (super.getService().exists(queryWrapper)) {
            Response.error(ResponseMessageEnum.USER_NAME_EXIST);
        }
        return super.preAdd(dto);
    }

}
