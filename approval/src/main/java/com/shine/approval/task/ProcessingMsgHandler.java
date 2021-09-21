package com.shine.approval.task;

import com.alibaba.fastjson.JSON;
import com.shine.approval.dao.entity.ApprovalDocument;
import com.shine.approval.service.IDataService;
import com.shine.approval.statemachine.approval.ApprovalEvents;
import com.shine.approval.statemachine.service.ApprovalStatemachineService;
import com.shine.common.exceptions.BaseException;
import com.shine.common.module.dto.ImageList;
import com.shine.common.module.dto.Output;
import com.shine.common.utils.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @version 1.0
 * @date 2020/5/8 21:41
 * fileName：AlgProviderRecvHandler
 * Use：算法生产的消息，消费处理
 */
@Component
public class ProcessingMsgHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    ApprovalStatemachineService approvalStatemachineService;
    @Resource
    IDataService dataService;

    /**
     * 解析算法那正确处理的消息
     *
     * @param message message
     */
    public void handlerAlgMessage(String message) {
        logger.info("开始算法处理成功消息...");
        String recordId = null;
        try {
            if (StringUtils.isNotEmpty(message) && Json.isJson(message)) {

                // 保存原始输出 json
                Object object = (message);

                // 处理输出
                String msg = message.replaceAll("\"smj\"", "\"扫描件\"")
                        .replaceAll("\"dzzz\"", "\"电子证照\"");
                Output savedOutput = JSON.parseObject(msg, Output.class);
                logger.info("0. 转换 Output 成功, 记录编号为: {}", savedOutput.getRecordId());

                // 保存旧版 json
                try {
                    recordId = saveOldOutput(savedOutput);
                    if (StringUtils.isBlank(recordId)) {
                        throw new BaseException("404", "recordId 为空");
                    }
                } catch (Exception e) {
                    logger.error("save old output fail...");
                    e.printStackTrace();
                    throw new Exception(e);
                }

                List<ApprovalDocument> approvalDocumentList;

                // 只分类的情况
                if (savedOutput.getCalcMode() == 1) {
                    // 获取审批材料
                    approvalDocumentList = getBatchScanDocList(recordId);
                    // 更新批量扫描图片的文档分类到 数据库
                    try {
                        updateBatchScanClassify(recordId, approvalDocumentList, savedOutput.getDocumentClassify());
                    } catch (Exception e) {
//                        execute(recordId, BatchEvents.PROCESS_ERROR_MSG);
                        throw new BaseException(e);
                    }

                    // 更新状态为处理完成
//                    execute(recordId, BatchEvents.PROCESS_SUCCESS_MSG);
                } else {
                    // 获取审批材料
                    approvalDocumentList = dataService.getSecondaryMaterialList(recordId);
                    // 清除材料忽略的mongo信息
                    clearIgnoreLog(recordId);

                    // 更新文档分类到 数据库
                    try {
                        updateDocClassify(recordId, approvalDocumentList, savedOutput.getDocumentClassify());
                    } catch (Exception e) {
                        approvalStatemachineService.execute(recordId, ApprovalEvents.PROCESS_ERROR_MSG);
                        throw new BaseException(e);
                    }

                    // 更新状态为处理完成
                    dataService.updateStatusFinished(recordId);
                    approvalStatemachineService.execute(recordId, ApprovalEvents.PROCESS_SUCCESS_MSG);
                }

                // 保存处理后的 json
                saveOutput(savedOutput, approvalDocumentList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("处理消息失败， 2分钟后重试");
        }
    }

    private void updateDocClassify(String recordId, List<ApprovalDocument> approvalDocumentList, Object documentClassify) {
    }

    private List<ApprovalDocument> getBatchScanDocList(String recordId) {
        return null;
    }

    private void updateBatchScanClassify(String recordId, List<ApprovalDocument> approvalDocumentList, List<ImageList> resultList) {
        logger.debug("[task][updateBatchScanClassify] - 更新算法分类成功的材料[seq][materialId][docName] {}", "");
    }

    private void clearIgnoreLog(String recordId) {
        logger.debug("[task] 删除ignore标记");
    }

    /**
     * 解析算法处理失败的消息
     *
     * @param message message
     */
    public void handlerAlgErrorMessage(String message) {
        logger.info("算法处理失败消息..., msg: {}", message);
    }

    private void saveOutput(Output savedOutput, List<ApprovalDocument> approvalDocumentList) {
        logger.debug("文档名称已更新...");
        logger.debug("[task][saveOutput] - {}", JSON.toJSONString(savedOutput, false));
    }

    public String saveOldOutput(Output savedOutput) {

        logger.info("1. Output 转换 AudioResult 成功, 记录编号为: {}", "result.getAudioRecordId()");
        logger.debug("2. Build AudioResultDto");
        logger.info("3. 保存到MongoDB成功, 记录编号为: {} ", "result.getAudioRecordId()");
        return "{}";
    }

}
