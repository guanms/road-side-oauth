package com.tingbei.common.constant;

/**
 * 标准请求授权类型的枚举
 */
public enum TokenStyleEnum {
    PASSWORD("password"),
    REFRESHTOKEN("refresh_token"),
    CODE("code");


    TokenStyleEnum(String style) {
        this.style = style;
    }

    private String style;

    public String getStyle() {
        return style;
    }
}
