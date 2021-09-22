package com.shine.approval.module.vo;

import lombok.Data;

/**
 * 信息提取
 *
 * @author zhaoyao
 */
@Data
public class InfoExtraVo {

    private String code;
    private String status;
    private String errorMsg;
    private String recordId;
    private Object extractOutput;
    private double timeCost;

}
