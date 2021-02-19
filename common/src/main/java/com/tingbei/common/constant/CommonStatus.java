package com.tingbei.common.constant;

/**
 * 通用状态枚举
 * Created by JJH on 2017/12/19.
 * @author JJH
 */
public enum CommonStatus {

    /**
     * 正常状态
     */
    NORAML("normal"),
    /**
     * 删除
     */
    DELETED("deleted"),
    /**
     * 发送短信的模版
     */

    TINGBEI("SMS_158945283"),
    TINGBEISIGN("停贝云平台"),
    ZHUCE("SMS_10405972"),
    ZHUCESIGN("注册验证"),
    XINXICHANGE("SMS_10405970"),
    XINXICHANGESIGN("变更验证"),
    CHANGEPWD("SMS_10405976"),
    CHANGEPWDSIGN("身份验证");

    private String status;

    CommonStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
