package com.example.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class MessageConsumer {

    /**
     * 通用消息处理方法
     */
    @RabbitListener(queues = "my.queue")
    public void handleMessage(Message message, Channel channel,
                            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            // 获取消息内容
            String content = new String(message.getBody());
            System.out.println("接收到消息: " + content);

            // 获取消息属性
            String contentType = message.getMessageProperties().getContentType();
            String messageId = message.getMessageProperties().getMessageId();

            System.out.println("消息属性 - ContentType: " + contentType + ", MessageId: " + messageId);

            // 根据内容类型处理不同的消息
            if (contentType != null && contentType.contains("json")) {
                // 处理JSON消息
                System.out.println("处理JSON消息");
            } else {
                // 处理文本消息
                System.out.println("处理文本消息");
            }

            // 模拟业务处理
            processBusinessLogic(content);

            // 手动确认消息
            channel.basicAck(deliveryTag, false);
            System.out.println("消息处理完成并已确认");
        } catch (Exception e) {
            System.err.println("处理消息时发生错误: " + e.getMessage());
            e.printStackTrace();
            try {
                // 拒绝消息并重新入队（最多重试3次）
                Long retryCount = (Long) message.getMessageProperties().getHeaders().getOrDefault("retry-count", 0L);
                if (retryCount < 3) {
                    channel.basicNack(deliveryTag, false, true);
                } else {
                    // 重试次数过多，记录错误并丢弃
                    System.err.println("消息重试次数过多，丢弃消息: " + retryCount);
                    channel.basicNack(deliveryTag, false, false);
                }
            } catch (IOException ioException) {
                System.err.println("确认消息失败: " + ioException.getMessage());
            }
        }
    }

    /**
     * 模拟业务处理逻辑
     */
    private void processBusinessLogic(String message) {
        // 模拟处理时间
        try {
            Thread.sleep(1000);
            System.out.println("业务处理完成: " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
