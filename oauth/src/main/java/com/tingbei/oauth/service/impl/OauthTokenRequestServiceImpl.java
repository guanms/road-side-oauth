package com.tingbei.oauth.service.impl;/**
 * @Author:JXW
 * @Date:2021/2/19 16:23
 */

import com.tingbei.common.constant.TokenStyleEnum;
import com.tingbei.common.util.GenerateUtil;
import com.tingbei.common.util.OauthTokenRequestUtil;
import com.tingbei.common.vo.AuthTokenInfo;
import com.tingbei.common.vo.OauthTokenRequestVO;
import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.oauth.service.OauthTokenRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 *@Author:JXW
 *@Date:2021/2/19 16:23
 */
@Service
public class OauthTokenRequestServiceImpl implements OauthTokenRequestService {

    @Autowired
    private OauthTokenRequestUtil oauthTokenRequestUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GenerateUtil generateUtil;

    @Override
    public AuthTokenInfo commonPasswordTokenRequest(OauthTokenRequestVO oauthTokenRequestVO) {
        return this.oauthTokenRequestUtil.getAccessTokenRequest(oauthTokenRequestVO, TokenStyleEnum.PASSWORD.getStyle());
    }

}
