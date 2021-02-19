package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoVO implements Serializable{
    private static final long serialVersionUID = 6079182457825620535L;
    /**
     * 用户唯一标识，用于之后的角色查询
     */
    private String uiUuid;
    private String userName;
    private String loginName;
    private String userAttribute;
    private String userStatus;
    private String userCreateMan;
    private String userCreateTime;
    private String identityCode;
    private Integer areaCode;
    private String specialMark;
    private String userType;
    /**
     * 页面新增用户时候需要password
     */
    private String password;
}
