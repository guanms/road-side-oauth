package com.tingbei.oauth.endPoint;/**
 * @Author:JXW
 * @Date:2021/2/19 13:16
 */

import com.tingbei.common.constant.RedisPrefix;
import com.tingbei.common.entity.OauthClientDetails;
import com.tingbei.common.util.GenerateUtil;
import com.tingbei.common.util.MD5Util;
import com.tingbei.common.vo.*;
import com.tingbei.common.vo.userresource.UserAndResourceVO;
import com.tingbei.oauth.service.OauthTokenRequestService;
import com.tingbei.oauth.service.UserLoginService;
import com.tingbei.oauth.util.AesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 *@Author:JXW
 *@Date:2021/2/19 13:16
 */
@RestController
public class UserLoginEndPoint {

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private OauthTokenRequestService oauthTokenRequestService;

    @Autowired
    private GenerateUtil generateUtil;

    @Autowired
    private MD5Util md5Util;

    @Autowired
    private RedisTemplate redisTemplate;



    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @CrossOrigin
    @PostMapping("/UserLogin")
    public ServiceResultVO userLogin(String loginName,String password){
        if (null == loginName || null == password || StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
            //表单数据有误则还返回到登录页
            return generateUtil.generageServiceResultVO(false,null,"用户名密码不能为空",null);
        }else {
            //获取加密后的密码
            loginName= AesUtil.aesDecrypt(loginName);
            password=AesUtil.aesDecrypt(password);
            logger.info("解密后数据loginName"+loginName);
            logger.info("解密后数据password"+password);
            password = md5Util.getMD5ofStr(password);

            UserInfoVO userInfo = userLoginService.queryOneUserInfo(loginName,password);
            if(null != userInfo){
                // 用户名密码验证成功，1. 去请求token，2. 去跨服务调用鉴权，返回该用户的所有权限
                OauthTokenRequestVO oauthTokenRequestVO = new OauthTokenRequestVO();

                //根据用户名查询客户端信息
//                OauthClientDetails oauthClientDetails = userLoginService.queryClientInfo(userInfo.getIdentityCode());
//                if(null != oauthClientDetails) {
//                    oauthTokenRequestVO.setClientId(oauthClientDetails.getClientId());
//                    oauthTokenRequestVO.setClientSecret(oauthClientDetails.getClientSecret());
//                }
                oauthTokenRequestVO.setClientId("ac");
                oauthTokenRequestVO.setClientSecret("ac");
                oauthTokenRequestVO.setUserName(loginName);
                oauthTokenRequestVO.setPassword(password);
                AuthTokenInfo authTokenInfo = GetCommonPasswordToken(oauthTokenRequestVO);
                if(null != authTokenInfo){
                    TokenAndResourceVo tokenAndResourceVo = new TokenAndResourceVo();
                    UserAndResourceVO userAndResourceVO = this.checkAuthorityClient.noRoleCheckByLoginName(loginName);
                    tokenAndResourceVo.setAuthTokenInfo(authTokenInfo);
                    tokenAndResourceVo.setUserAndResourceVO(userAndResourceVO);
                    String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + authTokenInfo.getAccess_token();
                    redisTemplate.opsForValue().set(redisKey,tokenAndResourceVo,authTokenInfo.getExpires_in(), TimeUnit.SECONDS);
                    return generateUtil.generageServiceResultVO(true,null,"登陆成功",tokenAndResourceVo);
                }else {
                    return generateUtil.generageServiceResultVO(false,null,"授权错误，请联系管理员",null);
                }
            }else {
                return generateUtil.generageServiceResultVO(false,null,"用户名密码不正确",null);
            }
        }
    }

    private AuthTokenInfo GetCommonPasswordToken(OauthTokenRequestVO oauthTokenRequestVO) {
        if(!StringUtils.isBlank(oauthTokenRequestVO.getUserName()) && !StringUtils.isBlank(oauthTokenRequestVO.getPassword())
                && !StringUtils.isBlank(oauthTokenRequestVO.getClientId()) && !StringUtils.isBlank(oauthTokenRequestVO.getClientSecret())){
            try {
                return oauthTokenRequestService.commonPasswordTokenRequest(oauthTokenRequestVO);
            } catch (Exception e) {
                logger.error("OauthTokenEndpoint:::commonPasswordTokenRequest:::接口执行异常",e);
            }
        }else {
            logger.error("OauthTokenEndpoint:::commonPasswordTokenRequest:::接口参数不合法！");
        }
        return null;
    }


}
