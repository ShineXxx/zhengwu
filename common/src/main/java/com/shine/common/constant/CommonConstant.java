package com.shine.common.constant;


/**
 * @author Berry_Cooper.
 * @date 2018-03-26
 * Description 公共常量
 */
public final class CommonConstant {

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

        public String getDesc() {
            return this.desc;
        }
    }
}
