package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // 定义队列
    @Bean
    public Queue myQueue() {
        return new Queue("my.queue", true); // true表示持久化
    }

    // 定义交换机
    @Bean
    public TopicExchange myExchange() {
        return new TopicExchange("my.exchange");
    }

    // 绑定队列和交换机 - 关键配置
    @Bean
    public Binding binding(Queue myQueue, TopicExchange myExchange) {
        return BindingBuilder.bind(myQueue).to(myExchange).with("my.routing.key");
    }

    // 配置消息确认回调
    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback() {
        return (correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息发送成功: " + correlationData);
            } else {
                System.err.println("消息发送失败: " + cause);
            }
        };
    }

    // 配置消息返回回调（新版本方法）
    @Bean
    public RabbitTemplate.ReturnsCallback returnsCallback() {
        return returned -> {
            System.err.println("消息退回 - Exchange: " + returned.getExchange() +
                    ", RoutingKey: " + returned.getRoutingKey() +
                    ", ReplyCode: " + returned.getReplyCode() +
                    ", ReplyText: " + returned.getReplyText() +
                    ", Message: " + new String(returned.getMessage().getBody()));
        };
    }

    // 配置消息转换器
    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(mapper);
    }

    // 配置 RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback());
        rabbitTemplate.setReturnsCallback(returnsCallback()); // 使用新方法
        rabbitTemplate.setMessageConverter(jsonMessageConverter());

        // 配置重试机制
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);

        return rabbitTemplate;
    }

    // 配置监听器容器工厂
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 手动确认
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setPrefetchCount(1);
        return factory;
    }
}
