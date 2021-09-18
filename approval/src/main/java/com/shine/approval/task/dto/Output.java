package com.shine.approval.task.dto;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 算法输出结果
 *
 * @author zhaoyao
 */
@Data
@ToString
public class Output {
    String recordId;
    Integer calcMode;

    public List<ImageList> getDocumentClassify() {
        return new ArrayList<>();
    }
}
