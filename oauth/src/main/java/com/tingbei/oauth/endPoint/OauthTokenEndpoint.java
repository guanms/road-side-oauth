package com.tingbei.oauth.endPoint;

import com.tingbei.common.util.GenerateUtil;
import com.tingbei.common.vo.AuthTokenInfo;
import com.tingbei.common.vo.OauthTokenRequestVO;
import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.oauth.service.OauthTokenRequestService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring-serurity-oauth
 * 请求授权的接口外层处理封装
 * Created by JJH on 2017/5/9.
 * @author JJH
 */
@RestController
@RequestMapping("/vapor/oauthToken")
public class OauthTokenEndpoint {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OauthTokenRequestService oauthTokenRequestService;

    @Autowired
    private GenerateUtil generateUtil;

    @PostMapping("/common/password")
    public AuthTokenInfo commonPasswordTokenRequest(@RequestBody OauthTokenRequestVO oauthTokenRequestVO){
        logger.info("OauthTokenEndpoint:::commonPasswordTokenRequest:::接口收到的请求参数为：" + oauthTokenRequestVO);
        if(!StringUtils.isBlank(oauthTokenRequestVO.getUserName()) && !StringUtils.isBlank(oauthTokenRequestVO.getPassword())
                && !StringUtils.isBlank(oauthTokenRequestVO.getClientId()) && !StringUtils.isBlank(oauthTokenRequestVO.getClientSecret())){
            try {
                return this.oauthTokenRequestService.commonPasswordTokenRequest(oauthTokenRequestVO);
            } catch (Exception e) {
                logger.error("OauthTokenEndpoint:::commonPasswordTokenRequest:::接口执行异常",e);
            }
        }else {
            logger.error("OauthTokenEndpoint:::commonPasswordTokenRequest:::接口参数不合法！");
        }
        return null;
    }
}
