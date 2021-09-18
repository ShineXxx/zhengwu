package com.shine.approval.mongo.document;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/10/23 14:01
 * fileName：AudioErrorDocument
 * Use：
 */
@Data
@Document("audioError")
public class AudioErrorDocument {

    @Id
    private String id;

    @JSONField(name = "create_time")
    private Date createTime;

    private ErrorDto error;

    @Data
    public static class ErrorDto {
        private String sid;
        private String audioRecordId;

        private String body;

        @JsonProperty("calc_mode")
        private String calcMode;
    }

}
