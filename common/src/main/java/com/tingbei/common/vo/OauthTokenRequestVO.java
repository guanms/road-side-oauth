package com.tingbei.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户端请求授权的外层封装请求参数
 * Created by JJH on 2017/5/9.
 * @author JJH
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OauthTokenRequestVO implements Serializable{
    private static final long serialVersionUID = -8482220577349282869L;
    /**
     * 登录名 （是user_info中的loginName，考虑之前的通用，暂没有将字段名改成loginName）
     */
    private String userName;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * 要刷新则要refresh_token
     */
    private String refreshToken;
    /**
     * code换取access_token需要code和redirect_uri回调函数
     */
    private String code;
    /**
     * 第三方授权回掉url
     */
    private String redirectUri;
}
