package com.tingbei.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 授权用户信息列表（spring security oauth2.0 使用的用户信息已更改为user_info）
 */
@Data
public class OauthUsers implements Serializable{
    private static final long serialVersionUID = 6184056053116713049L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "SELECT nextval('oauth_users_user_no_seq')")
    private Long userNo;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String enabled; // 1可用 0不可用
    @Column
    private String authority;
    @Column
    private String userEmail;
    @Column
    private String userPhone;
    @Column
    private String clientId;
    @Column
    private String appId;
}
