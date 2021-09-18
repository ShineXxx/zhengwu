package com.shine.approval.service.impl;

import com.shine.approval.dao.entity.ApprovalDocument;
import com.shine.approval.service.ApprovalService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyao
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {
    @Override
    public void commit(Object o) {

    }

    @Override
    public List<ApprovalDocument> getAllApprovalDocListByReceiptRecordId(String recordId) {
        return new ArrayList<>();
    }
}
