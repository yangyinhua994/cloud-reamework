package com.example.controller;

import com.example.producer.MessageProducer;
import com.example.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 消息队列管理接口
 */
@RestController
@RequestMapping("/user/api/rabbitmq")
@RequiredArgsConstructor
public class RabbitMQController {

    private final MessageProducer messageProducer;

    /**
     * 发送简单消息
     */
    @PostMapping("/send/simple")
    public Response<Void> sendSimpleMessage(@RequestParam String message) {
        messageProducer.sendSimpleMessage(message);
        return Response.success();
    }

    /**
     * 发送带确认的消息
     */
    @PostMapping("/send/confirmed")
    public Response<Void> sendConfirmedMessage(@RequestParam String message) {
        messageProducer.sendConfirmedMessage(message);
        return Response.success();
    }

}
