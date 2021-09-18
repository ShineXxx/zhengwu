package com.shine.approval.mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Berry_Cooper.
 * @date 2019/8/22 14:02
 * fileName：AudioResultDTO
 * Use：
 */
@Data
@Document(collection = "audioResult")
public class AudioResultDto {

    @Id
    private String audioResultId;

    private Date audioDate;

    private Object audioResult;

}
