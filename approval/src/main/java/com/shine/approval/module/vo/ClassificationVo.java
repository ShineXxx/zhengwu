package com.shine.approval.module.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 图片分类任务
 *
 * @author zhaoyao
 */
@Data
public class ClassificationVo {

    /**
     * 预审记录ID
     */
    @NotNull
    private String recordId;

    /**
     * 帮办结构化数据
     */
    @NotNull
    private Map<String, Object> structuredData;
}
