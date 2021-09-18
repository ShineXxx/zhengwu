package com.shine.approval.statemachine.approval;

/**
 * @author: zhj
 * @Date: 2020/10/13 16:05
 * @Description: 智能审批状态流程 触发点
 */
public enum ApprovalEvents {
    /**
     * 初始化
     */
    INIT("初始化"),
    RECEIVE("受理办件"),
    RECEIVE_FROM_JAVA("接收到Java任务"),
    DOCUMENT_CLASSIFICATION("文档分类"),
    INFORMATION_EXTRACTION("信息提取"),
    RULE_CALL("规则判断"),
    PROCESS_SUCCESS_MSG("保存算法处理成功的消息"),
    PROCESS_ERROR_MSG("保存算法处理失败的消息");

    private String desc;

    ApprovalEvents(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
