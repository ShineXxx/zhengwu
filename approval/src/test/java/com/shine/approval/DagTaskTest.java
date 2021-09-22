package com.shine.approval;

import com.shine.approval.module.vo.CommitVo;
import com.shine.approval.service.IApprovalService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
public class DagTaskTest {

    @Resource
    IApprovalService approvalService;

    @Test
    void contextLoads() throws IOException {
        approvalService.commitV2(new CommitVo());
        System.in.read();
    }
}
