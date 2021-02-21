package com.tingbei.signup.service;/**
 * @Author:JXW
 * @Date:2021/2/19 13:17
 */

import com.tingbei.common.vo.UserInfoVO;

/**
 *@Author:JXW
 *@Date:2021/2/19 13:17
 */
public interface UserLoginService {

    UserInfoVO queryOneUserInfo(String loginName, String password);
}
