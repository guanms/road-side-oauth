package com.tingbei.oauth.service;/**
 * @Author:JXW
 * @Date:2021/2/19 16:23
 */

import com.tingbei.common.vo.AuthTokenInfo;
import com.tingbei.common.vo.OauthTokenRequestVO;
import com.tingbei.common.vo.ServiceResultVO;

/**
 *@Author:JXW122121212121212121
 *@Date:2021/2/19 16:23
 */
public interface OauthTokenRequestService {

    AuthTokenInfo commonPasswordTokenRequest(OauthTokenRequestVO oauthTokenRequestVO);

}
