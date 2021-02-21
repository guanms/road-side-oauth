package com.tingbei.oauth.util;


import com.tingbei.oauth.config.OauthTokenUrlConfig;
import com.tingbei.common.constant.TokenStyleEnum;
import com.tingbei.common.vo.AuthTokenInfo;
import com.tingbei.common.vo.OauthTokenRequestVO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * spring-sercurity-oauth 请求授权
 * 公共请求工具类
 * Created by JJH on 2017/5/9.
 * @author JJH
 */
@Component
public class OauthTokenRequestUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private OauthTokenUrlConfig oauthTokenUrlConfig;

    /**
     * 注入
     */
    @Autowired
    public OauthTokenRequestUtil(OauthTokenUrlConfig oauthTokenUrlConfig){
        this.oauthTokenUrlConfig = oauthTokenUrlConfig;
    }

    /**
     * Prepare HTTP Headers.
     * @return httpHeaders
     */
    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /**
     * 配置请求时的头，在Authorization中带上clientid和secret.
     * @param clientId 客户端id
     * @param clientSecret 客户端secret
     * @return 返回httpHeader
     */
    private HttpHeaders getHeadersWithClientCredentials(String clientId, String clientSecret){
        //例如："acme:acme"
        String plainClientCredentials=clientId + ":" + clientSecret;
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    /**
     * password模式的请求授权
     * @param requestVO 请求参数
     * @return 授权实体
     */
    public AuthTokenInfo getAccessTokenRequest(OauthTokenRequestVO requestVO, String tokenStyle){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeadersWithClientCredentials(requestVO.getClientId(),requestVO.getClientSecret()));
        String url = "";
        if(TokenStyleEnum.PASSWORD.getStyle().equals(tokenStyle)){
            //password 模式
            url = this.oauthTokenUrlConfig.getPasswordAccessToken() + "&username="+ requestVO.getUserName() + "&password=" + requestVO.getPassword();
        }else if(TokenStyleEnum.CODE.getStyle().equals(tokenStyle)){
            //code 换取access_token
            url = this.oauthTokenUrlConfig.getCodeAccessToken() + "&code="+ requestVO.getCode() + "&redirect_uri=" + requestVO.getRedirectUri();
        }else if(TokenStyleEnum.REFRESHTOKEN.getStyle().equals(tokenStyle)){
            url = this.oauthTokenUrlConfig.getRefreshAccessToken() + requestVO.getRefreshToken();
        }
        AuthTokenInfo tokenInfo = restTemplate.postForObject(url,request,AuthTokenInfo.class);
        if(null != tokenInfo){
            logger.info("spring-sercurity-oauth 请求授权返回的结果是"+tokenInfo);
        }else {
            logger.error("spring-sercurity-oauth 请求授权返回body为空");
        }

        return tokenInfo;
    }


//    public AuthTokenInfo refreshAccessTokenRequest(String refreshToken){
//        if(!StringUtils.isBlank(refreshToken)){
//
//        }
//        return null;
//    }
}
