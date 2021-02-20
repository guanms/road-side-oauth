package com.tingbei.oauth.endPoint;/**
 * @Author:JXW
 * @Date:2021/2/20 14:21
 */

import com.github.pagehelper.PageInfo;
import com.tingbei.common.vo.RoleInfoExtendVO;
import com.tingbei.common.vo.SaveUserRoleRequestVO;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.common.vo.UserPageQueryParam;
import com.tingbei.oauth.service.UserRoleService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/20 14:21
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleEndPoint {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/pageQueryAll")
    public PageInfo<UserInfoVO> pageQueryUserInfo(@RequestBody UserPageQueryParam pageParam){
        PageInfo<UserInfoVO> pageInfo = null;
        if(null != pageParam && !StringUtils.isBlank(pageParam.getRedisId())){
            try {
                pageInfo = userRoleService.pageQueryUserInfo(pageParam);
            } catch (Exception e) {
                logger.error("UserRoleOperateEndpoint:::pageQueryRoleInfo:::接口异常",e);
            }
        }else {
            logger.error("UserRoleOperateEndpoint:::pageQueryRoleInfo:::接口参数不合法");
        }
        return pageInfo;
    }

    @PostMapping("/findUserRoleByUiUuid")
    public List<RoleInfoExtendVO> findUserRoleByUserName(String uiUuid, String riRoleDesc, String redisId){
        if(!StringUtils.isBlank(uiUuid) && !StringUtils.isBlank(redisId)){
            try {
                return userRoleService.findUserRoleByUserName(uiUuid,riRoleDesc,redisId);
            } catch (Exception e) {
                logger.error("UserRoleOperateEndpoint:::findUserRoleByUserName:::接口异常",e);
            }
        }else {
            logger.error("UserRoleOperateEndpoint:::findUserRoleByUserName:::接口参数不合法！");
        }
        return null;
    }

    @PostMapping("/saveUserRole")
    public String saveUserRole(@RequestBody SaveUserRoleRequestVO requestVO){
        logger.info("UserRoleOperateEndpoint:::saveUserRole:::接口接收到的参数为:" + requestVO);
        if(null != requestVO && !StringUtils.isBlank(requestVO.getUiUuid())){
            try {
                return userRoleService.saveUserRole(requestVO.getUiUuid(),requestVO.getOwnerRoleList());
            } catch (Exception e) {
                logger.error("UserRoleOperateEndpoint:::saveUserRole:::接口异常",e);
            }
        }else {
            logger.error("UserRoleOperateEndpoint:::saveUserRole:::接口参数不合法！");
        }
        return "fail";
    }
}
