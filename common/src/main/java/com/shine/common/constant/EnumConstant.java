package com.shine.common.constant;


/**
 * @author Berry_Cooper.
 * @date 2018-03-26
 * Description 公共常量
 */
public final class EnumConstant {

    public enum ApprovalResult {

        /**
         * 审批结果
         */
        Fuzzy("F"),
        Wrong("W"),
        Right("R");

        private String type;

        ApprovalResult(String type) {
            this.type = type;
        }

        public static ApprovalResult fromTypeName(String type) {
            for (ApprovalResult item : ApprovalResult.values()) {
                if (item.getType().equals(type)) {
                    return item;
                }
            }
            return null;
        }

        public String getType() {
            return this.type;
        }
    }

    public enum ExecuteState {
        /**
         * 审批结果
         */
        received("已受理"),
        processing("处理中"),
        fail("处理失败"),
        finished("处理完成");

        private String desc;

        ExecuteState(String desc) {
            this.desc = desc;
        }

        public static ExecuteState getNameByDesc(String desc) {
            for (ExecuteState item : ExecuteState.values()) {
                if (item.getDesc().equals(desc)) {
                    return item;
                }
            }
            return null;
        }

        public static String getDescByName(String name) {
            for (ExecuteState item : ExecuteState.values()) {
                if (item.name().equals(name)) {
                    return item.getDesc();
                }
            }
            return null;
        }

        public String getDesc() {
            return this.desc;
        }
    }

    public enum DocSourceType {
        /**
         * 材料来源支持枚举
         */
        SCAN("扫描"),
        FILE("文件"),
        CERT("证照库"),
        BB_GENERATE("帮办制作"),
        BB_UPLOAD("用户自备");

        private String desc;

        public String getDesc() {
            return desc;
        }

        DocSourceType(String desc) {
            this.desc = desc;
        }

        public static String getNameByDesc(String desc) {
            for (DocSourceType item : DocSourceType.values()) {
                if (item.getDesc().equals(desc)) {
                    return item.name();
                }
            }
            return null;
        }

    }

    public enum SubmitType {
        /**
         * 提交审批类型，全流程第一次审核=1， 全流程第二次审核=2
         */
        HALF(1, "全流程第一次审核"),
        FULL(2, "全流程第二次审核");

        private Integer value;
        private String desc;

        public String getDesc() {
            return desc;
        }

        public Integer getValue() {
            return value;
        }

        SubmitType(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    public enum ApplyerType {
        /**
         * 申请人类型枚举
         */
        // 个人
        PERSON("20", "个人事项"),
        // 企业
        COMPANY("10", "法人事项"),
        // 通用类型
        GENERAL("30", "通用类型");

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        ApplyerType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}