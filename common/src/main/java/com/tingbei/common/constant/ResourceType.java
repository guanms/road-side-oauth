package com.tingbei.common.constant;

/**
 * 大类资源类型枚举
 * （使用范围资源： scope  服务资源： service）
 *  (操作级别)
 */
public enum ResourceType {
    /**
     * 使用范围资源
     */
    SCOPE("scope"),

    /**
     * 服务资源
     */
    SERVICE("service"),

    /**
     * 操作级别：可选择
     */
    SELECT("select"),

    /**
     * 操作级别：可展示
     */
    SHOW("show");

    private String type;

    ResourceType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
