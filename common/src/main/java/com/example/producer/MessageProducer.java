package com.example.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendSimpleMessage(String message) {
        try {
            rabbitTemplate.convertAndSend("my.queue", message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendConfirmedMessage(String message) {
        try {
            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend("my.exchange", "my.routing.key", message, correlationData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
