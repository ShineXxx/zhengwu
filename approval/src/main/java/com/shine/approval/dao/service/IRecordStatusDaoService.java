package com.shine.approval.dao.service;


import com.shine.approval.dao.entity.RecordStatus;

/**
 * 记录状态dao服务
 *
 * @author zhaoyao
 */
public interface IRecordStatusDaoService {
    /**
     * 更新记录状态
     *
     * @param valueOf
     * @param name
     */
    void updateCurrentState(Long valueOf, String name);

    /**
     * 获取记录状态
     *
     * @param recordId
     * @return
     */
    String getCurrentStateString(Long recordId);

    /**
     * 保存记录状态
     *
     * @param state
     */
    void save(RecordStatus state);
}
