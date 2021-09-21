package com.shine.algorithm.listener;

import cn.hutool.core.lang.UUID;
import com.rabbitmq.client.Channel;
import com.shine.algorithm.service.OutputProducer;
import com.shine.common.module.dto.Input;
import com.shine.common.module.dto.Output;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 模拟算法消费服务
 *
 * @author zhaoyao
 */
@Slf4j
@Service
public class TaskReceiver {


    @Resource
    OutputProducer outputProducer;

    @RabbitListener(queues = "task.input.requests")
    public void receiveInput(Input input, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        System.out.println(" [x] Received request for  " + input.getRecordId());
        // 封装任务结果返回结构体
        Output output = new Output();
        output.setRecordId(UUID.fastUUID().toString());
        output.setCalcMode(1);
        // 模拟耗时任务
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 发送OUTPUT
        outputProducer.send(output);
        // 手动ACK
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
