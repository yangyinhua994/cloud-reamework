package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.convert.PickupAddressConvert;
import com.example.dto.PickupAddressDTO;
import com.example.entity.PickupAddress;
import com.example.groups.Add;
import com.example.groups.Update;
import com.example.response.Response;
import com.example.service.PickupAddressService;
import com.example.vo.PickupAddressVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 取货地址管理接口
 */
@RestController
@RequestMapping("/base/pickup-address")
@Validated
public class PickupAddressController extends BaseController<PickupAddress, PickupAddressDTO, PickupAddressVO, PickupAddressService, PickupAddressConvert> {
    public PickupAddressController(PickupAddressService service, PickupAddressConvert convert) {
        super(service, convert);
    }
}
