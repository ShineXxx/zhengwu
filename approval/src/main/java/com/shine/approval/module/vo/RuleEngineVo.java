package com.shine.approval.module.vo;

import lombok.Data;

/**
 * 规则引擎任务
 *
 * @author zhaoyao
 */
@Data
public class RuleEngineVo {
    private String code;
    private String status;
    private String errorMsg;
    private String recordId;
    private Object extractOutput;
    private double timeCost;
}
