package com.shine.approval.service.impl;

import com.shine.approval.service.IApprovalService;
import org.springframework.stereotype.Service;

/**
 * @author zhaoyao
 */
@Service
public class ApprovalServiceImpl implements IApprovalService {
    @Override
    public String init(String materialName) {
        return "";
    }

    @Override
    public void commit(Object o) {

    }
}
