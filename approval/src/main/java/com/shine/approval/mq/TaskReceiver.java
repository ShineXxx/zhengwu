package com.shine.approval.mq;

import com.rabbitmq.client.Channel;
import com.shine.common.module.dto.Output;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
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

    @RabbitListener(queues = "task.output.requests")
    public void receiveInput(Output output, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        System.out.println(" [x] Received request for  " + output.getRecordId());
        // 模拟耗时任务
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 手动ACK
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
