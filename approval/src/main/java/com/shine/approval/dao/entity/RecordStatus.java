package com.shine.approval.dao.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author zhaoyao
 */
@Data
@ToString
public class RecordStatus {

    private Integer id;

    private Long recordId;

    /**
     * 审批状态
     */
    private String recordStatus;

    /**
     * 推送材料分类状态
     */
    private String iadStatus;

    /**
     * 推送看件单状态
     */
    private String iaiStatus;

    /**
     * 状态消息
     */
    private String statusMessage;

    /**
     * 创建时间
     */
    private Date createTime;

    private Date updateTime;
}
