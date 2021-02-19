package com.tingbei.signup.service;

import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UserInfoVO;

public interface RegisterService {

    UserInfoVO queryOneUserInfoByUserName(String loginName) throws Exception;

    ServiceResultVO createUserInfo(String identityCode, String username, String createMan, String version);

}
