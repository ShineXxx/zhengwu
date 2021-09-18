package com.shine.approval.task.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 任务input
 *
 * @author zhaoyao
 */
@Data
@ToString
public class Input {
    private String version;
    private Integer calcMode;
    private String recordId;
    private String approvalId;
    private List<InputImage> inputImages;

    @Data
    @ToString
    public static class InputImage {
        private String imageId;
        private String documentId;
    }

}
