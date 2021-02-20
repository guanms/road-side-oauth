package com.tingbei.oauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 标准授权请求url配置
 * Created by JJH on 2017/5/9.
 */
@Component
@Data
@ConfigurationProperties("oauth")
public class OauthTokenUrlConfig {
    private String passwordAccessToken;
    private String codeAccessToken;
    private String refreshAccessToken;
}
