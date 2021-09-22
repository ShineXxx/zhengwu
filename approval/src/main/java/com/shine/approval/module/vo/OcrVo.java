package com.shine.approval.module.vo;

import com.shine.common.constant.EnumConstant;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 图片OCR入参
 *
 * @author zhaoyao
 */
@Data
public class OcrVo {
    @NotBlank
    private String recordId;
    /**
     * 所有图片列表
     */
    @Valid
    @NotEmpty
    private List<ImageInfo> imageInfo;

    @Data
    @ToString
    public static class ImageInfo {
        /**
         * PDF OR JPG
         */
        @NotBlank
        private String url;

        // 以下都是可选的 optional

        /**
         * 图片名称
         */
        private String imageName;

        /**
         * 二级材料分类ID
         */
        private Long documentId;

        /**
         * 一级材料ID
         */
        private String materialId;

        /**
         * 来源类型
         * SCAN -"扫描件"
         * CERT -"电子证照"
         * GENERATE -"制作"
         * GENERATE_NEED_DETECT -"制作-需检测"
         */
        private String sourceType = EnumConstant.DocSourceType.SCAN.name();

        /**
         * 材料页码
         */
        private Integer pageNum;

        /**
         * 总页数
         */
        private Integer pageCount;

    }
}
