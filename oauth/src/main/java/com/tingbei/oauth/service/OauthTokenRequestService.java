package com.tingbei.oauth.service;/**
 * @Author:JXW
 * @Date:2021/2/19 16:23
 */

import com.tingbei.common.vo.AuthTokenInfo;
import com.tingbei.common.vo.OauthTokenRequestVO;

/**
 *@Author:JXW
 *@Date:2021/2/19 16:23
 */
public interface OauthTokenRequestService {

    AuthTokenInfo commonPasswordTokenRequest(OauthTokenRequestVO oauthTokenRequestVO);
}
