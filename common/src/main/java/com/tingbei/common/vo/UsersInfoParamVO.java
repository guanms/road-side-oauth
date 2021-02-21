package com.tingbei.common.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 端口接收的用户信息实体
 */
@Data
public class UsersInfoParamVO implements Serializable{
    private static final long serialVersionUID = 285134092980504480L;
    private String userName;
    private String password;
    private String enabled; // 1可用 0不可用
    private String authority; //权限
    private String userEmail;
    private String userPhone;
    private String clientId;
    private String appId;
}
