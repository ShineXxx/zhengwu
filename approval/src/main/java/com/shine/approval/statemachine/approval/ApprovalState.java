package com.shine.approval.statemachine.approval;

/**
 * @author: zhj
 * @Date: 2020/9/22 15:21
 * @Description: 智能审批状态
 */
public enum ApprovalState {
    /**
     * 已受理
     */
    RECEIVED("已受理", 0),
    PROCESSING("处理中", 0),
    RECEIVE_FROM_JAVA("接收到Java任务", 0),
    CLASSIFYING_DOCUMENTS("文档分类", 5),
    EXTRACTING_INFORMATION("信息提取", 35),
    RULE_APPROVAL("规则判断", 95),
    FINISHED("处理完成", 100),
    ERROR("处理失败", 100);

    private String desc;

    private int percent;

    ApprovalState(String desc, int percent) {
        this.desc = desc;
        this.percent = percent;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
