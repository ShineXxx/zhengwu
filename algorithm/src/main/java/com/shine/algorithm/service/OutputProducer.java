package com.shine.algorithm.service;

import cn.hutool.core.lang.UUID;
import com.shine.common.module.dto.Output;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OutputProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private DirectExchange producerExchange;

    public void send(Output output) {
        CorrelationData correlationData = new CorrelationData(UUID.fastUUID().toString());
        rabbitTemplate.convertAndSend
                (producerExchange.getName(), "output", output, correlationData);
    }
}
