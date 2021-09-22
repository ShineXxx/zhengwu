package com.shine.approval.service.impl;

import com.shine.approval.module.vo.ClassificationVo;
import com.shine.approval.module.vo.InfoExtraVo;
import com.shine.approval.module.vo.OcrVo;
import com.shine.approval.module.vo.RuleEngineVo;
import com.shine.approval.service.IApprovalOptimizeService;
import org.springframework.stereotype.Service;

/**
 * @author zhaoyao
 */
@Service
public class ApprovalOptimizeServiceImpl implements IApprovalOptimizeService {

    @Override
    public String ocrTask(OcrVo ocrVo) {
        return null;
    }

    @Override
    public String classificationTask(ClassificationVo classificationVo) {
        return null;
    }

    @Override
    public String informationExtractionTask(InfoExtraVo infoExtraVo) {
        return null;
    }

    @Override
    public String ruleEngineTask(RuleEngineVo ruleEngineVo) {
        return null;
    }
}
