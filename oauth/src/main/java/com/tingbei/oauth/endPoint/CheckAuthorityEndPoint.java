package com.tingbei.oauth.endPoint;/**
 * @Author:JXW
 * @Date:2021/2/20 20:20
 */

import com.tingbei.common.vo.userresource.UserAndResourceVO;
import com.tingbei.oauth.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@Author:JXW
 *@Date:2021/2/20 20:20
 */
@RestController
@RequestMapping("/authority")
public class CheckAuthorityEndPoint {

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/noRole/checkByLoginName/{loginName}")
    public UserAndResourceVO noRoleCheckByLoginName(@PathVariable("loginName") String loginName){
        return this.authorityService.queryUserAllResource(loginName);
    }
}
