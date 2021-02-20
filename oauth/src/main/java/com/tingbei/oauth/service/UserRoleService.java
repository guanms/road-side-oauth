package com.tingbei.oauth.service;

import com.github.pagehelper.PageInfo;
import com.tingbei.common.vo.RoleInfoExtendVO;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.common.vo.UserPageQueryParam;

import java.util.List;

public interface UserRoleService {
    /**
     * 分页查询角色信息
     * @param pageParam 查询参数
     * @return 返回RoleInfo的集合
     * @throws Exception 异常
     */
    PageInfo<UserInfoVO> pageQueryUserInfo(UserPageQueryParam pageParam) throws Exception;

    /**
     * 根据uiUuid用户标志查找已分配的角色信息
     * @param uiUuid 用户标志
     * @param riRoleDesc 角色描述
     * @param redisId 缓存id
     * @return 返回角色信息集合
     * @throws Exception 异常
     */
    List<RoleInfoExtendVO> findUserRoleByUserName(String uiUuid, String riRoleDesc, String redisId) throws Exception;

    /**
     *  保存用户的角色
     * @param uiUuid 用户id
     * @param ownerRoleList 角色集合
     * @return 返回  success| fail
     * @throws Exception 异常
     */
    String saveUserRole(String uiUuid,List<String> ownerRoleList) throws Exception;


}
