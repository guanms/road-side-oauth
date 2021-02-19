package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 适用范围资源表中抽取给用户返回的资源信息
 */
@Data
public class UseScopeVO implements Serializable{
    private static final long serialVersionUID = 6933946280987268932L;
    private String usUuid;
    private String usName;
    private String usCode;
    private String usType;
    private String usLevel;
    private String usFatherUuid;
    private String operateLevel;
}
