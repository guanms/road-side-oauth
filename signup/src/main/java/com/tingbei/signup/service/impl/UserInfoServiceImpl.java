package com.tingbei.signup.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tingbei.common.constant.RedisPrefix;
import com.tingbei.common.constant.UserInfoStatus;
import com.tingbei.common.constant.UserInfoType;
import com.tingbei.common.entity.UserExtendInfo;
import com.tingbei.common.entity.UserInfo;
import com.tingbei.common.entity.UserRoleInfo;
import com.tingbei.common.repository.UserExtendInfoRepository;
import com.tingbei.common.repository.UserInfoRepository;
import com.tingbei.common.repository.UserRoleInfoRepository;
import com.tingbei.common.util.GenerateUtil;
import com.tingbei.common.util.MD5Util;
import com.tingbei.common.vo.*;
import com.tingbei.signup.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private MD5Util md5Util;

    @Autowired
    private GenerateUtil generateUtil;

    @Autowired
    private UserExtendInfoRepository userExtendInfoRepository;

    @Autowired
    private UserRoleInfoRepository userRoleInfoRepository;


    @Override
    public PageInfo<UserInfoVO> pageFindUnauditedUserInfo(UserPageQueryParam userPageQueryParam) throws Exception {
        PageHelper.startPage(userPageQueryParam.getPageNumber(),userPageQueryParam.getPageSize());
        List<UserInfoVO> list = this.userInfoRepository.queryUnauditedUserInfoByUserNameFuzzy(userPageQueryParam.getUserName());
        return new PageInfo<>(list);
    }

    @Override
    public String updateUserStatus(String uiUuid,String status) throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUiUuid(uiUuid);
        userInfo.setUiStatus(status);
        int lines = this.userInfoRepository.updateByPrimaryKeySelective(userInfo);
        if(lines > 0){
            return "success";
        }
        return "fail";
    }

    @Override
    public PageInfo<UserInfoVO> pageFindAuditedUserInfo(UserPageQueryParam userPageQueryParam) throws Exception {
        String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + userPageQueryParam.getRedisId();
        TokenAndResourceVo tokenAndResourceVo = (TokenAndResourceVo) this.redisTemplate.opsForValue().get(redisKey);
        if(null != tokenAndResourceVo && null != tokenAndResourceVo.getUserAndResourceVO() && null != tokenAndResourceVo.getUserAndResourceVO().getUserInfo()){
            UserInfoVO userInfoVO = tokenAndResourceVo.getUserAndResourceVO().getUserInfo();
            PageHelper.startPage(userPageQueryParam.getPageNumber(),userPageQueryParam.getPageSize());
            List<UserInfoVO> list = this.userInfoRepository.queryAuditedUserInfoByUserNameFuzzy(userPageQueryParam.getUserName(),userInfoVO.getUserAttribute(),userInfoVO.getLoginName());
            return new PageInfo<>(list);
        }else {
            logger.error("UserInfoOperateServiceImpl:::pageFindAuditedUserInfo:::redis中查询不到用户信息");
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ServiceResultVO<String> saveOrUpdateUserFromPage(SaveUserRequestVO requestVO) throws Exception {
        // 根据redisId查询缓存的用户信息
        String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + requestVO.getRedisId();
        TokenAndResourceVo tokenAndResourceVo = (TokenAndResourceVo) this.redisTemplate.opsForValue().get(redisKey);
        if(null != tokenAndResourceVo && null != tokenAndResourceVo.getUserAndResourceVO() && null != tokenAndResourceVo.getUserAndResourceVO().getUserInfo()){
            UserInfoVO createMan = tokenAndResourceVo.getUserAndResourceVO().getUserInfo();
            if(!StringUtils.isBlank(requestVO.getUiUuid())){
                // update （更新用户属性和密码）
                UserInfo userInfo = new UserInfo();
                userInfo.setUiUuid(requestVO.getUiUuid());
                userInfo.setUiPassword(this.md5Util.getMD5ofStr(requestVO.getPassword()));
                userInfo.setUiAttribute(requestVO.getUserAttribute());
                this.userInfoRepository.updateByPrimaryKeySelective(userInfo);
                return this.generateUtil.generageServiceResultVO(true,null,"修改成功！","success");
            }else {
                // new add
                UserInfo userInfo = new UserInfo();
                userInfo.setUiUsername(requestVO.getUserName());
                userInfo.setUiLoginname(requestVO.getLoginName());
                userInfo.setUiPassword(this.md5Util.getMD5ofStr(requestVO.getPassword()));
                userInfo.setUiAttribute(requestVO.getUserAttribute());
                userInfo.setUiStatus(UserInfoStatus.NORAML.getStatus());
                userInfo.setUiCreateTime(new Date());
                userInfo.setUiCreateMan(createMan.getLoginName());
                userInfo.setIdentityCode(createMan.getIdentityCode());
                userInfo.setAreaCode(createMan.getAreaCode());
                // security 新增两个字段，需要token授权的用户要新增
                userInfo.setSecurityEnabled("1");
                userInfo.setSecurityAuthority("common");
                // 存user_info表
                this.userInfoRepository.insertSelective(userInfo);
                userInfo = this.userInfoRepository.selectOne(userInfo);
                UserExtendInfo userExtendInfo = new UserExtendInfo();
                userExtendInfo.setUiUuid(userInfo.getUiUuid());
                userExtendInfo.setUeiType(UserInfoType.WEB.getType());
                userExtendInfo.setIdentityCode(createMan.getIdentityCode());
                userExtendInfo.setAreaCode(createMan.getAreaCode());
                // 存 user_extend_info
                this.userExtendInfoRepository.insertSelective(userExtendInfo);

//                UserVersionRelation  u = new UserVersionRelation();
//                u.setUiUuid(createMan.getUiUuid());
//                u.setIdentityCode(createMan.getIdentityCode());
//                u.setUvrStatus("normal");
//                u =  userVersionRelationRepository.selectOne(u);
//
//                if(null!=u){
//                    UserVersionRelation userVersionRelation = new UserVersionRelation();
//                    userVersionRelation.setUiUuid(userInfo.getUiUuid());
//                    userVersionRelation.setVrUuid(u.getVrUuid());
//                    userVersionRelation.setIdentityCode(userInfo.getIdentityCode());
//                    userVersionRelation.setUvrStatus("normal");
//                    userVersionRelationRepository.insert(userVersionRelation);
//                }


                return this.generateUtil.generageServiceResultVO(true,null,"添加成功！","success");
            }
        }else {
            return this.generateUtil.generageServiceResultVO(false,null,"您没有权限，请联系管理员！","fail");
        }
    }

    @Override
    public Boolean checkUserNameCanUse(String userName,String uiUuid) throws Exception {
        List<UserInfo> checkUserNameList = this.userInfoRepository.checkUserNameCanUse(userName,uiUuid);
        return (checkUserNameList.size() <= 0);
    }

    @Override
    public Boolean checkLoginNameCanUse(String loginName,String uiUuid) throws Exception {
        List<UserInfo> checkLoginNameList = this.userInfoRepository.checkLoginNameCanUse(loginName,uiUuid);
        return (checkLoginNameList.size() <= 0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteUserInfo(String uiUuid) throws Exception {
        // 删除用户信息
        this.userInfoRepository.deleteByPrimaryKey(uiUuid);
        // 删除用户扩展表
        UserExtendInfo userExtendInfo = new UserExtendInfo();
        userExtendInfo.setUiUuid(uiUuid);
        this.userExtendInfoRepository.delete(userExtendInfo);
        // 删除用户角色关系表
        UserRoleInfo userRoleInfo = new UserRoleInfo();
        userRoleInfo.setUiUuid(uiUuid);
        this.userRoleInfoRepository.delete(userRoleInfo);
        return "success";
    }
}
