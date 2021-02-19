package com.tingbei.oauth.service.impl;/**
 * @Author:JXW
 * @Date:2021/2/19 13:18
 */

import com.tingbei.common.repository.UserLoginRepository;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.oauth.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@Author:JXW
 *@Date:2021/2/19 13:18
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public UserInfoVO queryOneUserInfo(String loginName, String password) {
        return userLoginRepository.findByLoginNameAndPassword(loginName,password);
    }
}
