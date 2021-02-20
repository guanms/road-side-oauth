package com.tingbei.common.constant;

/**
 * user_info 类型枚举
 */
public enum UserInfoType {
    /**
     * 安卓收费端
     */
    ANDROIDTOLL("androidToll"),
    /**
     * 派派盒子
     */
    PPBOX("ppBox"),
    /**
     * 手持设备
     */
    HANDPC("handPc"),
    /**
     * 网页
     */
    WEB("web"),
    /**
     * 微信
     */
    WEIXIN("weixin"),
    /**
     * 支付宝
     */
    ALIPAY("alipay"),
    /**
     * 建行卡
     */
    CCB("ccb"),
    /**
     * 手机端
     */
    MOBILE("mobile");

    private String type;

    UserInfoType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
