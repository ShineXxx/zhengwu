package com.shine.approval.service;

import com.shine.approval.module.vo.ClassificationVo;
import com.shine.approval.module.vo.InfoExtraVo;
import com.shine.approval.module.vo.OcrVo;
import com.shine.approval.module.vo.RuleEngineVo;

/**
 * 预审优化版 接口 （实现任务分片）
 *
 * @author zhaoyao
 */
public interface IApprovalOptimizeService {

    /**
     * 图片OCR任务
     *
     * @param ocrVo
     * @return
     */
    String ocrTask(OcrVo ocrVo);

    /**
     * 图片分类任务
     *
     * @param classificationVo
     * @return
     */
    String classificationTask(ClassificationVo classificationVo);

    /**
     * 信息提取任务
     *
     * @param infoExtraVo
     * @return
     */
    String informationExtractionTask(InfoExtraVo infoExtraVo);

    /**
     * 规则引擎任务
     *
     * @param ruleEngineVo
     * @return
     */
    String ruleEngineTask(RuleEngineVo ruleEngineVo);

}
