package com.shine.approval.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhaoyao
 * @date 2019/8/20 15:46
 * fileName：AsyncHandlerService
 * Use：异步处理服务
 */
@Slf4j
@Component
public class AsyncHandlerService {

    private final ProcessingMsgHandler processingMsgHandler;

    @Autowired
    public AsyncHandlerService(ProcessingMsgHandler processingMsgHandler) {
        this.processingMsgHandler = processingMsgHandler;
    }

    @Async("taskExecutor")
    public void handleMessage(String message) {
        log.info("开始处理success消息");
        long start = System.currentTimeMillis();
        processingMsgHandler.handlerAlgMessage(message);
        log.info("处理确认完毕, 耗时：{} ms", (System.currentTimeMillis() - start));
    }

    @Async("taskExecutor")
    public void handleErrorMessage(String message) {
        log.info("处理Fail消息");
        try {
            processingMsgHandler.handlerAlgErrorMessage(message);
        } catch (Exception e) {
            log.error("Java处理算法错误消息失败 ", e);
        }
        log.info("处理Fail消息完毕");
    }
}
