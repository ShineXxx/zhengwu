package com.shine.approval.service.impl;

import cn.hutool.core.lang.UUID;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.shine.approval.dagtask.AbstractTask;
import com.shine.approval.dagtask.DataContext;
import com.shine.approval.dagtask.FastTaskExecutor;
import com.shine.approval.dagtask.task.ClassificationTask;
import com.shine.approval.dagtask.task.InformationExtraTask;
import com.shine.approval.dagtask.task.OcrTask;
import com.shine.approval.dagtask.task.RuleEngineTask;
import com.shine.approval.dao.entity.ReceiptRecord;
import com.shine.approval.dao.entity.ReceiptRecordImage;
import com.shine.approval.module.vo.CommitVo;
import com.shine.approval.mq.InputProducer;
import com.shine.approval.service.IApprovalService;
import com.shine.common.module.dto.Input;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhaoyao
 */
@Service
public class ApprovalServiceImpl implements IApprovalService {

    @Resource
    InputProducer inputProducer;

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
    private static final ExecutorService threadPoolExecutor = new ThreadPoolExecutor(8, 32,
            500L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

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

    @Override
    public void commitV2(CommitVo o) {
        List<AbstractTask> tasks = new ArrayList<>();
        String ocr = UUID.fastUUID().toString();
        tasks.add(new OcrTask("", ocr, Lists.newArrayList()));
        String classification = UUID.fastUUID().toString();
        tasks.add(new ClassificationTask("", classification, Lists.newArrayList(ocr)));
        String info = UUID.fastUUID().toString();
        tasks.add(new InformationExtraTask("", info, Lists.newArrayList(classification)));
        String rule = UUID.fastUUID().toString();
        tasks.add(new RuleEngineTask("", rule, Lists.newArrayList(info)));
        DataContext dataContext = new DataContext();
        FastTaskExecutor.execute(tasks, dataContext, threadPoolExecutor);

    }

    private ReceiptRecord getRecord(String recordId) {
        return new ReceiptRecord();
    }
}
