package com.tingbei.oauth.service.impl;/**
 * @Author:JXW
 * @Date:2021/2/19 16:23
 */

import com.tingbei.common.vo.AuthTokenInfo;
import com.tingbei.common.vo.OauthTokenRequestVO;
import com.tingbei.oauth.service.OauthTokenRequestService;
import com.tingbei.oauth.util.OauthTokenRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author:JXW
 *@Date:2021/2/19 16:23
 */
@Service
public class OauthTokenRequestServiceImpl implements OauthTokenRequestService {

    @Autowired
    private OauthTokenRequestUtil oauthTokenRequestUtil;

    @Override
    public AuthTokenInfo commonPasswordTokenRequest(OauthTokenRequestVO oauthTokenRequestVO) {
        return null;
    }
}
