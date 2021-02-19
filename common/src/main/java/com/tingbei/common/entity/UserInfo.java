package com.tingbei.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfo implements Serializable{
    private static final long serialVersionUID = -5209245060952739479L;
    @Id
    @GeneratedValue(generator="UUID")
    private String uiUuid;
    @Column
    private String uiUsername;
    @Column
    private String uiLoginname;
    @Column
    private String uiPassword;
    @Column
    private String uiAttribute;
    @Column
    private String uiStatus;
    @Column
    private String uiCreateMan;
    @Column
    private Date uiCreateTime;
    @Column
    private String identityCode;
    @Column
    private Integer areaCode;
    /**
     * spring security oauth2.0授权新增两个字段
     * enabled 表示用户是否可用 1:可用  0:不可用
     * authority 表示权限 （与业务无关）
     */
    @Column
    private String securityEnabled;
    @Column
    private String securityAuthority;
}
