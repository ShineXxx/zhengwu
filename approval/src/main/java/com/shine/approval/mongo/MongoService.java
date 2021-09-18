package com.shine.approval.mongo;

import com.shine.approval.mongo.document.AudioResultDocument;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/8/21 14:37
 * fileName：MongoService
 * Use： mongodb 数据查询存储服务
 */
@Service
public class MongoService {
    private static final String AUDIT_RECORD_ID = "audioResult.audioRecordId";
    private static final String NOT_EXIST_ERROR = "结果不存在";
    private static final String UPDATE_ERROR = "更新失败";


    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 查询 mongo 中 原始json
     *
     * @param recordId 记录ID
     * @return
     */
    public AudioResultDocument queryOriginAuditResultJson(String recordId) {
        Query query = new Query(Criteria.where("_id").is(recordId));
        return mongoTemplate.findOne(query, AudioResultDocument.class);
    }

    private static final String APPROVAL_INFORMATION = "audioResult.approval_information";
    private static final String APPROVAL_POINT_ID = "approval_point_id";


    /**
     * 查找某一个 审批点 query
     *
     * @param receiptRecordId 记录ID
     * @param approvalPointId 审批点ID
     * @return
     */
    private Query getFindPointApprovalInfoQuery(String receiptRecordId, String approvalPointId) {
        return new Query(new Criteria().andOperator(
                Criteria.where(AUDIT_RECORD_ID).is(receiptRecordId),
                Criteria.where(APPROVAL_INFORMATION)
                        .elemMatch(Criteria.where(APPROVAL_POINT_ID)
                                .is(approvalPointId))));
    }

}
