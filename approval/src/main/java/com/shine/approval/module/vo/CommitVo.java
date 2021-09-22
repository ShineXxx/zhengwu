package com.shine.approval.module.vo;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhaoyao
 */
@Data
@ToString
public class CommitVo {

    @NotNull
    String recordId;
    List<CommitImageListVo> imageListList;

    @Data
    @ToString
    public static class CommitImageListVo {
        String url;
        String documentId;
    }
}
