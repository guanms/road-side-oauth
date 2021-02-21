package com.tingbei.common.client;


import com.tingbei.common.vo.UserAndRoleVO;
import com.tingbei.common.vo.userresource.UserAndResourceVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调用数据中心客户端
 */
@Component
@FeignClient("dc")
public interface CheckAuthorityClient {
    /**
     * 调用dc服务下的根据登录名检测用户权限
     * @param loginName 登录名
     * @return 返回用户权限
     */
    @RequestMapping(value = "/authority/checkByLoginName/{loginName}",method = RequestMethod.GET)
    UserAndRoleVO checkByLoginName(@PathVariable("loginName") String loginName);

    /**
     * 调用dc服务下的根据登录名检测用户资源列表
     * 资源列表为不重复的父子结构
     * @param loginName 登录名
     * @return 返回
     */
    @RequestMapping(value = "/authority/noRole/checkByLoginName/{loginName}",method = RequestMethod.GET)
    UserAndResourceVO noRoleCheckByLoginName(@PathVariable("loginName") String loginName);


    /**
     * 注册车场同步useScop
     * @param usName
     * @param usCode
     * @return
     */
    @RequestMapping(value ="/chainbi/putUseScopFromCarLane",method = RequestMethod.GET)
    String putUseScopFromCarLane(@RequestParam("usName") String usName, @RequestParam("usCode") String usCode);
}
