package com.shine.approval.service;

import com.shine.approval.dao.entity.ApprovalDocument;

import java.util.List;

/**
 * 基础数据服务
 *
 * @author zhaoyao
 */
public interface IDataService {

    /**
     * 获取所有材料
     *
     * @param recordId
     * @return
     */
    List<ApprovalDocument> getSecondaryMaterialList(String recordId);

    /**
     * 更新记录表状态
     *
     * @param recordId
     */
    void updateStatusFinished(String recordId);
}
