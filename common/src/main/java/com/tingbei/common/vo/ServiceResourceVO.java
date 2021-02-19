package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务资源表中抽取给用户返回的资源信息
 */
@Data
public class ServiceResourceVO implements Serializable{
    private static final long serialVersionUID = -3269814514762680553L;
    private String srUuid;
    private String srName;
    private String srCode;
    private String srVersion;
    private String srType;
    private Long srLevel;
    private String srUrl;
    private String srFatherUuid;
    /**
     * 增加authority表中的信息
     * 1. 操作级别
     */
    private String operateLevel;
}
