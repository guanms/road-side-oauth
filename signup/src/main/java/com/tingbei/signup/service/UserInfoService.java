package com.tingbei.signup.service;

import com.github.pagehelper.PageInfo;
import com.tingbei.common.vo.SaveUserRequestVO;
import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.common.vo.UserPageQueryParam;

public interface UserInfoService {

    /**
     * 分页查询未审核用户信息
     * @param userPageQueryParam 分页查询参数
     * @return 返回用户信息的分页信息
     * @throws Exception 异常
     */
    PageInfo<UserInfoVO> pageFindUnauditedUserInfo(UserPageQueryParam userPageQueryParam) throws Exception;

    /**
     * 更改用户信息状态
     * @param uiUuid 主键
     * @param status 状态
     * @return 返回 success | fail
     * @throws Exception 异常
     */
    String updateUserStatus(String uiUuid, String status) throws Exception;

    /**
     * 分页查询已审核用户信息
     * @param userPageQueryParam 分页查询参数
     * @return 返回用户信息的分页信息
     * @throws Exception 异常
     */
    PageInfo<UserInfoVO> pageFindAuditedUserInfo(UserPageQueryParam userPageQueryParam) throws Exception;

    /**
     * 新增或者更改用户信息
     * @param requestVO 参数
     * @return 返回通用返回函数
     * @throws Exception 异常
     */
    ServiceResultVO<String> saveOrUpdateUserFromPage(SaveUserRequestVO requestVO) throws Exception;

    /**
     * 检查用户名是否可用
     * @param userName 用户名
     * @param uiUuid 标识
     * @return 返回 true | false
     * @throws Exception 异常
     */
    Boolean checkUserNameCanUse(String userName, String uiUuid) throws Exception;

    /**
     * 检查用户名是否可用
     * @param loginName 登录名
     * @param uiUuid 标识
     * @return 返回 true | false
     * @throws Exception 异常
     */
    Boolean checkLoginNameCanUse(String loginName, String uiUuid) throws Exception;

    /**
     * 删除用户信息
     * @param uiUuid 主键
     * @return 返回 success | fail
     * @throws Exception 异常
     */
    String deleteUserInfo(String uiUuid) throws Exception;

}
