package com.tingbei.common.client;

import com.tingbei.common.vo.AuthTokenInfo;
import com.tingbei.common.vo.OauthTokenRequestVO;
import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UsersInfoParamVO;
import com.tingbei.common.vo.userresource.UserAndResourceVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 调用用户表客户端
 */
@Component
@FeignClient("oauth2Server")
public interface OauthServiceClient {

    /**
     * 调用oauth2Server服务的查询用户
     * @param usersInfoParamVO 参数
     * @return 返回
     */
    @RequestMapping(value = "/vapor/oauthUser/query",method = RequestMethod.POST)
    ServiceResultVO queryUserInfo(@RequestBody UsersInfoParamVO usersInfoParamVO);


    /**
     * 调用 oauth2Service 服务的插入用户
     * @param usersInfoParamVO 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "/vapor/oauthUser/insert",method = RequestMethod.POST)
    ServiceResultVO insertUserInfo(@RequestBody UsersInfoParamVO usersInfoParamVO);

    /**
     * 通用用户名密码请求token封装接口
     * @param oauthTokenRequestVO 请求参数
     * @return 返回token实体
     */
    @RequestMapping(value = "/vapor/oauthToken/common/password",method = RequestMethod.POST)
    AuthTokenInfo commonPasswordTokenRequest(@RequestBody OauthTokenRequestVO oauthTokenRequestVO);

    /**
     * 调用 oauth2Service 服务的更新用户
     * @param usersInfoParamVO 请求参数
     * @return 返回结果
     */
    @RequestMapping(value = "/vapor/oauthUser/update",method = RequestMethod.POST)
    ServiceResultVO updateUserInfoByName(@RequestBody UsersInfoParamVO usersInfoParamVO);

    /**
     * 调用dc服务下的根据登录名检测用户资源列表
     * 资源列表为不重复的父子结构
     * @param loginName 登录名
     * @return 返回
     */
    @RequestMapping(value = "/authority/noRole/checkByLoginName/{loginName}",method = RequestMethod.GET)
    UserAndResourceVO noRoleCheckByLoginName(@PathVariable("loginName") String loginName);
}
