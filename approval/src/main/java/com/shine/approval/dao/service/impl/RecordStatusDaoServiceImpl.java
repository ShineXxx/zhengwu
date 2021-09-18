package com.shine.approval.dao.service.impl;

import com.shine.approval.dao.entity.RecordStatus;
import com.shine.approval.dao.service.IRecordStatusDaoService;
import org.springframework.stereotype.Service;

/**
 * @author zhaoyao
 */
@Service
public class RecordStatusDaoServiceImpl implements IRecordStatusDaoService {
    @Override
    public void updateCurrentState(Long valueOf, String name) {

    }

    @Override
    public String getCurrentStateString(Long recordId) {
        return null;
    }

    @Override
    public void save(RecordStatus state) {

    }
}
