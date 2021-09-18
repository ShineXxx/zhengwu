package com.shine.approval.service;

import java.util.List;

/**
 * 查询服务
 *
 * @author zhaoyao
 */
public interface ISearchService {
    /**
     * 查询审批结果
     *
     * @param recordId
     * @return
     */
    List<Object> search(String recordId);
}
