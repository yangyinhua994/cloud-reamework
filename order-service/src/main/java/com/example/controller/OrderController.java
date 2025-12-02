package com.example.controller;

import com.example.convert.OrderConvert;
import com.example.dto.OrderDTO;
import com.example.entity.Order;
import com.example.service.OrderService;
import com.example.vo.OrderVO;
import org.springframework.web.bind.annotation.*;
/**
 * 订单管理接口
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController<Order, OrderDTO, OrderVO, OrderService, OrderConvert> {

    public OrderController(OrderService service, OrderConvert convert) {
        super(service, convert);
    }
}