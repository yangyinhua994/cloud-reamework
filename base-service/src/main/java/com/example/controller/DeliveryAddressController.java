package com.example.controller;

import com.example.convert.DeliveryAddressConvert;
import com.example.dto.DeliveryAddressDTO;
import com.example.entity.DeliveryAddress;
import com.example.service.DeliveryAddressService;
import com.example.vo.DeliveryAddressVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 收货地址管理接口
 */
@RestController
@RequestMapping("/base/delivery-address")
@Validated
public class DeliveryAddressController extends BaseController<DeliveryAddress, DeliveryAddressDTO, DeliveryAddressVO, DeliveryAddressService, DeliveryAddressConvert> {
    public DeliveryAddressController(DeliveryAddressService service, DeliveryAddressConvert convert) {
        super(service, convert);
    }
}
