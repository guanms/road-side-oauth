package com.tingbei.oauth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 标准授权请求url配置
 */
@Component
@Data
@ConfigurationProperties("oauth")
public class OauthTokenUrlConfig {
    private String passwordAccessToken;
    private String codeAccessToken;
    private String refreshAccessToken;
}
