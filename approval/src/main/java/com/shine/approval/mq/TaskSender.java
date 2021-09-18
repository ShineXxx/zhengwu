package com.shine.approval.mq;

import com.alibaba.fastjson.JSON;
import com.shine.approval.task.dto.Input;
import com.shine.approval.task.dto.Output;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
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
public class TaskSender {

    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private DirectExchange defaultExchange;

    /**
     * 异步处理任务
     *
     * @param input
     */
    @Async
    public void buildTask(Input input) {
        log.debug("新建线程-任务处理 {}", Thread.currentThread().getName());
        // todo 任务可中断
        Output rpc = (Output) rabbitTemplate.convertSendAndReceive
                (defaultExchange.getName(), "rpc", input);
        // save output
        log.info("任务处理完成 {}", JSON.toJSONString(rpc));
    }
}
