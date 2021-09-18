package com.shine.approval.service;

/**
 * 记录服务
 *
 * @author zhaoyao
 */
public interface ReceiptRecordService {
    /**
     * 设置记录状态为处理完成
     *
     * @param recordId
     */
    void setFinished(String recordId);
}
