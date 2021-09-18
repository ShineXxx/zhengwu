package com.shine.approval.task;

import com.shine.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/8/20 15:23
 * fileName：PendingQueueHandleRunner
 * Use：Java pending 队列扫描任务，启动后一直执行
 */
@Slf4j
@Component
public class AlgErrorQueueHandleRunner implements CommandLineRunner {

    private final AsyncHandlerService asyncHandlerService;

    @Autowired
    public AlgErrorQueueHandleRunner(AsyncHandlerService asyncHandlerService) {
        this.asyncHandlerService = asyncHandlerService;
    }

    @Async("taskExecutor")
    @Override
    public void run(String... args) throws InterruptedException {
        log.info("新建线程 {}", this.getClass().getName());
        while (true) {
            try {
                Result<Object> result = getMessageFromError();
                if ("SUCCESS".equalsIgnoreCase(result.getMsg())) {
                    String message = (String) result.getData();
                    if (StringUtils.isBlank(message)) {
                        Thread.sleep(2000);
                    } else {
                        asyncHandlerService.handleErrorMessage(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(2000);
            }
        }
    }

    private Result<Object> getMessageFromError() {
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Result<>();
    }
}
