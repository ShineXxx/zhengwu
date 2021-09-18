package com.shine.approval.dao.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 审批记录
 *
 * @author zhaoyao
 */
@Data
@ToString
public class ReceiptRecord {
    String recordId;
    String approvalId;
    String userId;
    String status;
    Date createTime;
    Date updateTime;
}
