package com.shine.approval.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/10/12 15:04
 * fileName：AudioResultDocumentA
 * Use：
 */
@Data
@Document(collection = "audioResult")
public class AudioResultDocument {
    @Id
    private String id;

    private Date createDate;

    private Object audioResult;
}
