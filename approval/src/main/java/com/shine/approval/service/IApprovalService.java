package com.shine.approval.service;

import com.shine.approval.module.vo.CommitVo;

/**
 * 审批服务
 *
 * @author zhaoyao
 */
public interface IApprovalService {

    /**
     * 初始化审批记录
     *
     * @param materialName
     * @return
     */
    String init(String materialName);

    /**
     * 提交材料
     *
     * @param o
     */
    void commit(CommitVo o);

    /**
     * 提交材料 版本2
     *
     * @param o
     */
    void commitV2(CommitVo o);

}
