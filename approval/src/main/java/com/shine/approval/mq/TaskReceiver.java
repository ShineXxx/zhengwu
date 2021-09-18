package com.shine.approval.mq;

import cn.hutool.core.lang.UUID;
import com.rabbitmq.client.Channel;
import com.shine.approval.task.dto.Input;
import com.shine.approval.task.dto.Output;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 模拟算法消费服务
 *
 * @author zhaoyao
 */
@Slf4j
@Service
public class TaskReceiver {

    @Autowired
    @Lazy
    TaskReceiver taskReceiver;

    @RabbitListener(queues = "task.rpc.requests", concurrency = "10")
    public Output receiveInput(Input input, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        System.out.println(" [x] Received request for " + input);
        Output output = new Output();
        output.setRecordId(UUID.fastUUID().toString());
        output.setCalcMode(1);
        taskReceiver.process();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("消费确认完成");
        return output;
    }

    @Async
    public void process() {
        log.info("process");
    }

}
