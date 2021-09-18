package com.shine.approval.dao.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 审批记录关联的图片
 *
 * @author zhaoyao
 */
@Data
@ToString
public class ReceiptRecordImage {
    String imageId;
    String url;
    String documentId;
}
