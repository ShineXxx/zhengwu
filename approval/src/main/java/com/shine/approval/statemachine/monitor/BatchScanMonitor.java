package com.shine.approval.statemachine.monitor;

import com.shine.approval.dao.service.IRecordStatusDaoService;
import com.shine.approval.statemachine.config.StatemachineApprovalConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.annotation.EventHeaders;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author zhaoyao
 * 批量扫描状态机 监听器
 */
@Slf4j
@Component
@WithStateMachine(id = StatemachineApprovalConfigurer.APPROVAL_STATEMACHINE_ID)
public class BatchScanMonitor {

    @Autowired
    IRecordStatusDaoService recordStatusDaoService;

    /**
     * 单个图片orc结束，更新百分比
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @OnTransition(source = "STAGED_IMPL", target = "STAGED_IMPL")
    public void singleOcrDone(@EventHeaders Map<String, Object> headers) {
        singleOcrProcess(headers);
    }

    /**
     * 单个图片orc结束，更新百分比
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @OnTransition(source = "BATCH_OCR", target = "STAGED_IMPL")
    public void singleOcrStart(@EventHeaders Map<String, Object> headers) {
        singleOcrProcess(headers);
    }

    private void singleOcrProcess(@EventHeaders Map<String, Object> headers) {
        // 更新完成百分比
        String recordId = (String) headers.get("recordId");
        // 更新批量上传图片完成状态
    }
}