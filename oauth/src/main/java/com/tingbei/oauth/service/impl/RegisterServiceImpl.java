package com.tingbei.oauth.service.impl;

import com.tingbei.common.entity.UserInfo;
import com.tingbei.common.repository.RegisterRepository;
import com.tingbei.common.util.CommonUtil;
import com.tingbei.common.util.GenerateUtil;
import com.tingbei.common.util.MD5Util;
import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UserInfoVO;

import com.tingbei.oauth.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterServiceImpl implements RegisterService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private GenerateUtil generateUtil;

    @Autowired
    private MD5Util md5Util;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public UserInfoVO queryOneUserInfoByUserName(String loginName) throws Exception {
        return this.registerRepository.findByLoginName(loginName);
    }

    @Override
    public ServiceResultVO createUserInfo(String identityCode, String username, String createMan, String version) {
        try {
            UserInfo userInfoNew = new UserInfo();

            String password = md5Util.getMD5ofStr(identityCode);
            //生成UUID
            userInfoNew.setUiUuid(commonUtil.generateUUID());
            userInfoNew.setUiLoginname(identityCode);
            userInfoNew.setUiUsername(username);
            userInfoNew.setUiPassword(password);
            //
            userInfoNew.setUiAttribute("SU");
            //这里默认给的状态是   unaudited   审核通过后，才会变成normal
            userInfoNew.setUiStatus("");
            userInfoNew.setUiCreateMan(createMan);
            userInfoNew.setIdentityCode(identityCode);
            //这里areaCode默认为1
            userInfoNew.setAreaCode(1);
            //表示用户状态是否可用，默认不可用
            userInfoNew.setSecurityEnabled("1");
            userInfoNew.setSecurityAuthority("common");

            logger.info("-------------注册用户:: identityCode："+identityCode+"  username:"+username+"  version:"+version+"----------------");

            registerRepository.saveUserInfo(userInfoNew);

            return this.generateUtil.generageServiceResultVO(true,"0","注册成功",null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.generateUtil.generageServiceResultVO(false,"-1","注册失败",null);
    }
}
