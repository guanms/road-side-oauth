package com.tingbei.oauth.service;

import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UsersInfoParamVO;

/**
 * 维护授权用户列表服务类
 */
public interface OauthUsersService {
    /**
     * 增加用户信息
     * @param usersInfoParamVO 需要增加的用户信息实体
     * @return ServiceResultVO通用服务返回类
     */
    ServiceResultVO insertUserInfo(UsersInfoParamVO usersInfoParamVO);

    /**
     * 根据用户名删除用户信息
     * @param userName 用户名
     * @return ServiceResultVO通用服务返回类
     */
    ServiceResultVO deleteUserInfoByName(String userName);

    /**
     * 根据用户名更新用户信息
     * @param usersInfoParamVO 用户信息
     * @return ServiceResultVO通用服务返回类
     */
    ServiceResultVO updateUserInfoByName(UsersInfoParamVO usersInfoParamVO);

    ServiceResultVO queryUsersInfo(UsersInfoParamVO usersInfoParamVO);
}
