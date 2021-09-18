package com.shine.approval.statemachine.batchscan;

/**
 * @author: zhj
 * @Date: 2020/9/22 15:21
 * @Description: 智能审批状态
 */
public enum BatchState {
    /**
     * 已受理
     */
    RECEIVED("已受理", 0),
    PROCESSING("处理中", 0),
    RECEIVE_FROM_JAVA("接收到Java任务", 0),
    CLASSIFYING_DOCUMENTS("文档分类", 5),
    BATCH_OCR("OCR识别中", 10),
    STAGED_IMPL("扫描图片中", 10),
    START_CLASSIFICATION("开始执行分类", 90),
    FINISHED("处理完成", 100),
    ERROR("处理失败", 100);

    private String desc;

    private int percent;

    BatchState(String desc, int percent) {
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
