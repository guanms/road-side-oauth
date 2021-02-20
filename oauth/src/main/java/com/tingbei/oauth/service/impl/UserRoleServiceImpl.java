package com.tingbei.oauth.service.impl;/**
 * @Author:JXW
 * @Date:2021/2/20 14:23
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tingbei.common.constant.RedisPrefix;
import com.tingbei.common.entity.UserRoleInfo;
import com.tingbei.common.repository.UserInfoRepository;
import com.tingbei.common.repository.UserRoleInfoRepository;
import com.tingbei.common.util.CommonUtil;
import com.tingbei.common.vo.RoleInfoExtendVO;
import com.tingbei.common.vo.TokenAndResourceVo;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.common.vo.UserPageQueryParam;
import com.tingbei.oauth.service.UpdateUserResourceCacheService;
import com.tingbei.oauth.service.UserRoleService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/20 14:23
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserRoleInfoRepository userRoleInfoRepository;

    @Autowired
    private UpdateUserResourceCacheService updateUserResourceCacheService;


    @Override
    public PageInfo<UserInfoVO> pageQueryUserInfo(UserPageQueryParam pageParam) {
        String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + pageParam.getRedisId();
        TokenAndResourceVo tokenAndResourceVo = (TokenAndResourceVo) redisTemplate.opsForValue().get(redisKey);
        if(null != tokenAndResourceVo && null != tokenAndResourceVo.getUserAndResourceVO() && null != tokenAndResourceVo.getUserAndResourceVO().getUserInfo()){
            UserInfoVO userInfoVO = tokenAndResourceVo.getUserAndResourceVO().getUserInfo();
            PageHelper.startPage(pageParam.getPageNumber(),pageParam.getPageSize());
            List<UserInfoVO> list = userInfoRepository.queryNormalUserInfoByUserNameFuzzy(pageParam.getUserName(),userInfoVO.getUserAttribute(),userInfoVO.getLoginName());
            return new PageInfo<>(list);
        }else {
            logger.error("UserRoleOperateServiceImpl:::pageQueryUserInfo:::redis中查询不到用户信息");
            return null;
        }
    }

    @Override
    public List<RoleInfoExtendVO> findUserRoleByUserName(String uiUuid, String riRoleDesc, String redisId) {
        String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + redisId;
        TokenAndResourceVo tokenAndResourceVo = (TokenAndResourceVo)this.redisTemplate.opsForValue().get(redisKey);
        if(null != tokenAndResourceVo && null != tokenAndResourceVo.getUserAndResourceVO() && null != tokenAndResourceVo.getUserAndResourceVO().getUserInfo()){
            UserInfoVO userInfoVO = tokenAndResourceVo.getUserAndResourceVO().getUserInfo();
            if(!StringUtils.isBlank(userInfoVO.getUserName()) && !StringUtils.isBlank(userInfoVO.getUserAttribute())){
                if("SP".equals(userInfoVO.getUserAttribute())){
                    return userRoleInfoRepository.findUserRoleByUserName(uiUuid,riRoleDesc);
                }else {
                    return userRoleInfoRepository.findSuUserRoleByUserName(uiUuid,riRoleDesc,userInfoVO.getLoginName());
                }
            }else {
                logger.error("findUserRoleByUserName:::未从redis中找到登录用户的用户名和用户属性！");
                return null;
            }
        }else {
            logger.error("findUserRoleByUserName:::redis中没有找到所需信息！");
            return null;
        }
    }


    @Override
    public String saveUserRole(String uiUuid, List<String> ownerRoleList) {
        int lines = 0;
        // 1.删除该用户原先的角色关系
        UserRoleInfo del = new UserRoleInfo();
        del.setUiUuid(uiUuid);
        lines += userRoleInfoRepository.delete(del);
        // 2.插入新的角色
        if(null != ownerRoleList && ownerRoleList.size() > 0){
            List<UserRoleInfo> list = new ArrayList<>();
            for (String riUuid:ownerRoleList) {
                UserRoleInfo userRoleInfo = new UserRoleInfo();
                userRoleInfo.setUiUuid(uiUuid);
                userRoleInfo.setRiUuid(riUuid);
                userRoleInfo.setUriUuid(commonUtil.generateUUID());
                list.add(userRoleInfo);
            }
            lines += userRoleInfoRepository.insertBatch(list);
        }
        if(lines > 0){
            updateUserResourceCacheService.updateUserResCacheByRiuuidOrUiuuid(null,uiUuid);
            return "success";
        }
        return "fail";
    }

}
