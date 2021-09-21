package com.shine.algorithm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ 配置类
 *
 * @author zhaoyao
 */
@Configuration
public class RabbitMqConfig {
    /**
     * 发送JSON格式对象
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        // 请求超时时间
        rabbitTemplate.setReplyTimeout(100000);
        // RPC不使用临时队列
        rabbitTemplate.setUseTemporaryReplyQueues(false);
//        rabbitTemplate.setUserCorrelationId(true);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean("producerExchange")
    public DirectExchange exchange() {
        return new DirectExchange("task.output");
    }

    @Bean
    public Queue queue() {
        return new Queue("task.output.requests");
    }

    @Bean
    public Binding binding(DirectExchange exchange,
                           Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("output");
    }
}
