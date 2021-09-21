package com.shine.approval;

import cn.hutool.core.io.file.FileReader;
import com.shine.approval.mq.InputProducer;
import com.shine.approval.task.AsyncHandlerService;
import com.shine.common.module.dto.Input;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@SpringBootTest
class ApprovalApplicationTests {

    @Resource
    AsyncHandlerService asyncHandlerService;

    @Test
    void contextLoads() throws IOException {
        ClassPathResource resource = new ClassPathResource("out.json");
        File file = resource.getFile();
        boolean exists = file.exists();
        if (exists) {
            FileReader fileReader = new FileReader(file);
            String result = fileReader.readString();
            asyncHandlerService.handleMessage(result);
        }
    }

    @Resource
    InputProducer inputProducer;

    @Test
    void send() throws IOException {
        for (int i = 0; i < 4; i++) {
            Input input = new Input();
            input.setRecordId(UUID.randomUUID().toString());
            inputProducer.buildTask(input);
        }
        System.in.read();
    }
}
