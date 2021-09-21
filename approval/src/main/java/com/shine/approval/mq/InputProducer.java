package com.shine.approval.mq;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.shine.common.module.dto.Input;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 异步任务发送端
 *
 * @author zhaoyao
 */
@Slf4j
@Service
public class InputProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private DirectExchange producerExchange;

    /**
     * 异步处理任务
     *
     * @param input
     */
    @Async
    public void buildTask(Input input) {
        log.debug("线程池中的线程-任务处理 {}", Thread.currentThread().getName());
        CorrelationData correlationData = new CorrelationData(UUID.fastUUID().toString());
        // todo 任务可中断
        rabbitTemplate.convertAndSend
                (producerExchange.getName(), "input", input, correlationData);
        // save output
        log.info("任务发送完成");
    }
}
