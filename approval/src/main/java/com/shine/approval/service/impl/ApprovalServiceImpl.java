package com.shine.approval.service.impl;

import cn.hutool.core.lang.UUID;
import com.shine.approval.dao.entity.ReceiptRecord;
import com.shine.approval.dao.entity.ReceiptRecordImage;
import com.shine.approval.module.CommitVo;
import com.shine.approval.service.IApprovalService;
import com.shine.approval.mq.InputProducer;
import com.shine.common.module.dto.Input;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoyao
 */
@Service
public class ApprovalServiceImpl implements IApprovalService {

    @Resource
    InputProducer inputProducer;

    @Override
    public String init(String materialName) {
        // 初始化记录
        ReceiptRecord record = new ReceiptRecord();
        record.setRecordId(UUID.fastUUID().toString());
        // save
        return record.getRecordId();
    }

    @Override
    public void commit(CommitVo vo) {
        // 获取审批记录
        ReceiptRecord record = getRecord(vo.getRecordId());
        // 组装基础数据
        List<CommitVo.CommitImageListVo> imageListList = vo.getImageListList();
        List<ReceiptRecordImage> receiptRecordImages = new ArrayList<>();
        for (CommitVo.CommitImageListVo listVo : imageListList) {
            ReceiptRecordImage image = new ReceiptRecordImage();
            image.setDocumentId(listVo.getDocumentId());
            image.setImageId(UUID.fastUUID().toString());
            image.setUrl(listVo.getUrl());
            receiptRecordImages.add(image);
        }
        // save imageList

        // 封装任务体
        Input input = new Input();
        input.setVersion("1");
        input.setApprovalId("1");
        input.setCalcMode(1);
        input.setRecordId(vo.getRecordId());
        input.setApprovalId(record.getApprovalId());
        List<Input.InputImage> inputImages = new ArrayList<>();
        input.setInputImages(inputImages);
        for (ReceiptRecordImage image : receiptRecordImages) {
            Input.InputImage inputImage = new Input.InputImage();
            inputImage.setImageId(UUID.fastUUID().toString());
            inputImage.setDocumentId(image.getDocumentId());
            inputImages.add(inputImage);
        }
        // 异步构建和发送任务
        inputProducer.buildTask(input);
    }

    private ReceiptRecord getRecord(String recordId) {
        return new ReceiptRecord();
    }
}
