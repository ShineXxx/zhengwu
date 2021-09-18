package com.shine.approval.service;

import com.shine.approval.dao.entity.ApprovalDocument;

import java.util.List;

/**
 * 审批服务
 *
 * @author zhaoyao
 */
public interface ApprovalService {

    /**
     * 提交材料
     *
     * @param o
     */
    void commit(Object o);

    /**
     * 获取所有材料
     *
     * @param recordId
     * @return
     */
    List<ApprovalDocument> getAllApprovalDocListByReceiptRecordId(String recordId);
}
