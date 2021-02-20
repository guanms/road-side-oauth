package com.tingbei.common.constant;

/**
 * user_info 状态枚举
 */
public enum UserInfoStatus {
    /**
     * 未审核 （刚注册）
     */
    UNAUDITED("unaudited"),
    /**
     * 审核中
     */
    AUDITING("auditing"),
    /**
     * 审核未通过
     */
    NOTPASS("notpass"),
    /**
     * 正常状态 （审核通过状态）
     */
    NORAML("normal"),
    /**
     * 删除
     */
    DELETED("deleted"),
    /**
     *这里parkCode 对应user_info表中的 identity_code
     */
    IDENTITYCODE("identityCode"),
    /**
     *这里parkName 对应user_info表中的 ui_username
     */
    USERNAME("username"),
    /**
     *这里parkVersion 对应user_info表中的 ui_attribute
     */
    ATTRIBUTE("attribute"),
    /**
     * createMan 对应user_info表中的create_man
     */
    CREATEMAN("createMan");

    private String status;

    UserInfoStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
