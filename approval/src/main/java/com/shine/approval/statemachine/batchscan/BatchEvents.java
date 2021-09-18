package com.shine.approval.statemachine.batchscan;

/**
 * @author: zhj
 * @Date: 2020/10/13 16:05
 * @Description: 智能审批状态流程 触发点
 */
public enum BatchEvents {
    /**
     * 初始化
     */
    INIT("初始化"),
    RECEIVE("受理办件"),
    RECEIVE_FROM_JAVA("接收到Java任务"),
    DOCUMENT_CLASSIFICATION("文档分类"),
    GET_OCR_RESULT("OCR识别"),
    SINGLE_OCR_DONE("单张图片识别结束"),
    EXECUTE_CLASSIFICATION("开始执行分类"),
    PROCESS_SUCCESS_MSG("保存算法处理成功的消息"),
    PROCESS_ERROR_MSG("保存算法处理失败的消息");

    private String desc;

    BatchEvents(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
