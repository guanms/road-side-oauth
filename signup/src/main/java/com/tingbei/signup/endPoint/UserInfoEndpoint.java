package com.tingbei.signup.endPoint;


import com.github.pagehelper.PageInfo;
import com.tingbei.common.vo.SaveUserRequestVO;
import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.common.vo.UserPageQueryParam;
import com.tingbei.signup.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo/operate")
public class UserInfoEndpoint {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/pageFindAudited")
    public PageInfo<UserInfoVO> pageFindAuditedUserInfo(@RequestBody UserPageQueryParam userPageQueryParam){
        if(null != userPageQueryParam && !StringUtils.isBlank(userPageQueryParam.getRedisId())){
            try {
                return this.userInfoService.pageFindAuditedUserInfo(userPageQueryParam);
            } catch (Exception e) {
                logger.error("UserInfoOperateEndpoint:::pageFindAuditedUserInfo:::分页查询用户信息接口异常",e);
            }
        }else {
            logger.error("UserInfoOperateEndpoint:::pageFindAuditedUserInfo:::分页查询用户信息接口参数不合法");
        }
        return null;
    }

    @GetMapping("/checkUserNameCanUse")
    public Boolean checkUserNameCanUse(String userName,String uiUuid){
        if(!StringUtils.isBlank(userName)){
            try {
                return this.userInfoService.checkUserNameCanUse(userName,uiUuid);
            } catch (Exception e) {
                logger.error("UserInfoOperateEndpoint:::checkUserNameCanUse:::接口异常",e);
            }
        }else {
            logger.error("UserInfoOperateEndpoint:::checkUserNameCanUse:::接口参数不合法");
        }
        return false;
    }

    @GetMapping("/checkLoginNameCanUse")
    public Boolean checkLoginNameCanUse(String loginName,String uiUuid){
        if(!StringUtils.isBlank(loginName)){
            try {
                return this.userInfoService.checkLoginNameCanUse(loginName,uiUuid);
            } catch (Exception e) {
                logger.error("UserInfoOperateEndpoint:::checkLoginNameCanUse:::接口异常",e);
            }
        }else {
            logger.error("UserInfoOperateEndpoint:::checkLoginNameCanUse:::接口参数不合法");
        }
        return false;
    }

    /**
     * 授权中心前台新增或者更新用户信息
     * 有uiUUid则修改，没有则新增
     * @param saveUserRequestVO 用户信息
     * @return 返回success | fail
     */
    @PostMapping("/saveUser/fromPage")
    public ServiceResultVO<String> saveOrUpdateUserFromPage(@RequestBody SaveUserRequestVO saveUserRequestVO){
        logger.info("UserInfoOperateEndpoint:::saveOrUpdateUserFromPage:::接口收到的参数为：" + saveUserRequestVO);
        if(null != saveUserRequestVO && !StringUtils.isBlank(saveUserRequestVO.getUserName()) && !StringUtils.isBlank(saveUserRequestVO.getLoginName()) && !StringUtils.isBlank(saveUserRequestVO.getRedisId())){
            try {
                return this.userInfoService.saveOrUpdateUserFromPage(saveUserRequestVO);
            } catch (Exception e) {
                logger.error("UserInfoOperateEndpoint:::saveOrUpdateUserFromPage:::接口异常",e);
            }
        }else {
            logger.error("UserInfoOperateEndpoint:::saveOrUpdateUserFromPage:::接口参数不合法");
        }
        return null;
    }

    @PostMapping("/delete")
    public String deleteUserInfo(String uiUuid){
        if(!StringUtils.isBlank(uiUuid)){
            try {
                return this.userInfoService.deleteUserInfo(uiUuid);
            } catch (Exception e) {
                logger.error("UserInfoOperateEndpoint:::deleteUserInfo:::接口异常",e);
            }
        }else {
            logger.error("UserInfoOperateEndpoint:::deleteUserInfo:::接口参数不合法");
        }
        return "fail";
    }



}
