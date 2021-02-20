package com.tingbei.oauth.service;


import com.tingbei.common.vo.UserAndRoleVO;
import com.tingbei.common.vo.userresource.UserAndResourceVO;


public interface AuthorityService {
    /**
     * 查询某个用户所有的角色和资源权限
     * @param loginName 用户登录名称
     * @param identityCode 身份编码
     * @return 返回用户资源
     */
    UserAndRoleVO queryUserAllRoleResource(String loginName, String identityCode);

    /**
     * 查询用户的所有资源列表
     * @param loginName 登录名
     * @param identityCode 身份编码
     * @return 返回资源
     */
    UserAndResourceVO queryUserAllResource(String loginName, String... identityCode);

    /**
     * 更新queryUserAllResource方法的缓存，页面操作了用户的相关角色分配或者资源分配
     * 需要更新缓存
     * @param loginName 登录名
     * @param identityCode 身份编码
     * @return 返回
     */
    UserAndResourceVO updateCacheQueryUserAllResource(String loginName, String... identityCode);
}
