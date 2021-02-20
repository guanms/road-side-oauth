package com.tingbei.oauth.endPoint;

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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/userRole")
public class UserRoleEndpoint {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 分页查询用户信息
     * 根据用户名查询（授权中心正确的登录逻辑和用户信息暂不知，先给手机对账用）
     * @param pageParam 参数
     * @return 返回结果
     */
    @PostMapping("/pageQueryAll")
    public PageInfo<UserInfoVO> pageQueryUserInfo(@RequestBody UserPageQueryParam pageParam){
        PageInfo<UserInfoVO> pageInfo = null;
        if(null != pageParam && !StringUtils.isBlank(pageParam.getRedisId())){
            try {
                pageInfo = this.userRoleService.pageQueryUserInfo(pageParam);
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
                return this.userRoleService.findUserRoleByUserName(uiUuid,riRoleDesc,redisId);
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
                return this.userRoleService.saveUserRole(requestVO.getUiUuid(),requestVO.getOwnerRoleList());
            } catch (Exception e) {
                logger.error("UserRoleOperateEndpoint:::saveUserRole:::接口异常",e);
            }
        }else {
            logger.error("UserRoleOperateEndpoint:::saveUserRole:::接口参数不合法！");
        }
        return "fail";
    }









}
