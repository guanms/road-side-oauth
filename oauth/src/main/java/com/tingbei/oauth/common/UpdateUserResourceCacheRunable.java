package com.tingbei.oauth.common;

import com.tingbei.common.entity.UserInfo;
import com.tingbei.common.repository.UserInfoRepository;
import com.tingbei.common.repository.UserRoleInfoRepository;
import com.tingbei.oauth.service.AuthorityService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UpdateUserResourceCacheRunable implements Runnable{
    private String riUuid;
    private String uiUuid;
    private UserRoleInfoRepository userRoleInfoRepository;
    private UserInfoRepository userInfoRepository;
    private AuthorityService authorityService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public UpdateUserResourceCacheRunable(String riUuid,UserRoleInfoRepository userRoleInfoRepository,UserInfoRepository userInfoRepository,
                                          AuthorityService authorityService,String uiUuid){
        this.riUuid = riUuid;
        this.userRoleInfoRepository = userRoleInfoRepository;
        this.userInfoRepository = userInfoRepository;
        this.authorityService = authorityService;
        this.uiUuid = uiUuid;
    }

    @Override
    public void run() {
        try{
            if(null != uiUuid && !StringUtils.isBlank(uiUuid)){
                UserInfo userInfo = new UserInfo();
                userInfo.setUiUuid(uiUuid);
                userInfo = this.userInfoRepository.selectOne(userInfo);
                this.authorityService.updateCacheQueryUserAllResource(userInfo.getUiLoginname());
            }else {
                List<String> list = this.userRoleInfoRepository.findUserUuidByRoleUuid(riUuid);
                if(null != list && list.size() > 0){
                    List<UserInfo> userInfos = this.userInfoRepository.findUserInfosByUuids(list);
                    for (UserInfo userInfo:userInfos){
                        this.authorityService.updateCacheQueryUserAllResource(userInfo.getUiLoginname());
                    }
                }
            }
            logger.info("更新用户资源列表缓存成功");
        }catch (Exception e){
            logger.error("UpdateUserResourceCacheRunable:::线程更新用户资源列表缓存出错",e);
        }
    }

}
