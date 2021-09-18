package com.shine.approval.service.impl;

import com.shine.approval.dao.entity.ApprovalDocument;
import com.shine.approval.service.IDataService;
import com.shine.approval.service.ReceiptRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据服务实现类
 *
 * @author zhaoyao
 */
@Service
public class DataServiceImpl implements IDataService {

    @Resource
    ReceiptRecordService receiptRecordService;

    @Override
    public List<ApprovalDocument> getSecondaryMaterialList(String recordId) {
        return new ArrayList<>();
    }

    @Override
    public void updateStatusFinished(String recordId) {
        receiptRecordService.setFinished(recordId);
    }
}
