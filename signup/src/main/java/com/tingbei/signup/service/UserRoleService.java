package com.tingbei.signup.service;/**
 * @Author:JXW
 * @Date:2021/2/20 14:23
 */

import com.github.pagehelper.PageInfo;
import com.tingbei.common.vo.RoleInfoExtendVO;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.common.vo.UserPageQueryParam;

import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/20 14:23
 */
public interface UserRoleService {
    PageInfo<UserInfoVO> pageQueryUserInfo(UserPageQueryParam pageParam);

    List<RoleInfoExtendVO> findUserRoleByUserName(String uiUuid, String riRoleDesc, String redisId);

    String saveUserRole(String uiUuid, List<String> ownerRoleList);
}
