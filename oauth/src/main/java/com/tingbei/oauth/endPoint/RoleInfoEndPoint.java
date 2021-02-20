package com.tingbei.oauth.endPoint;/**
 * @Author:JXW
 * @Date:2021/2/19 16:35
 */

import com.github.pagehelper.PageInfo;
import com.tingbei.common.entity.RoleInfo;
import com.tingbei.common.vo.SaveAuthorityRequestVO;
import com.tingbei.common.vo.role.ResourceTreeVO;
import com.tingbei.common.vo.role.RolePageQueryParam;
import com.tingbei.common.vo.role.SaveRoleInfoRequestVO;
import com.tingbei.oauth.service.RoleInfoService;
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
 *@Date:2021/2/19 16:35
 */
@RestController
@RequestMapping("/role")
public class RoleInfoEndPoint {

    @Autowired
    private RoleInfoService roleInfoService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/pageQueryAll")
    public PageInfo<RoleInfo> pageQueryRoleInfo(@RequestBody RolePageQueryParam pageParam){
        PageInfo<RoleInfo> pageInfo = null;
        if(null != pageParam && !StringUtils.isBlank(pageParam.getRedisId())){
            try {
                pageInfo = roleInfoService.pageQueryRoleInfo(pageParam);
            } catch (Exception e) {
                logger.error("RoleOperateEndpoint:::pageQueryRoleInfo:::接口异常",e);
            }
        }else {
            logger.error("RoleOperateEndpoint:::pageQueryRoleInfo:::接口参数不合法");
        }
        return pageInfo;
    }

    @PostMapping("/delete")
    public String deleteRoleInfo(String riUuid){
        if(!StringUtils.isBlank(riUuid)){
            try {
                return roleInfoService.deleteRoleInfo(riUuid);
            } catch (Exception e) {
                logger.error("RoleOperateEndpoint:::deleteRoleInfo:::删除角色信息接口异常",e);
            }
        }else {
            logger.error("RoleOperateEndpoint:::deleteRoleInfo:::删除角色信息接口参数不合格!");
        }
        return "fail";
    }

    @PostMapping("/save")
    public String saveRoleInfo(@RequestBody SaveRoleInfoRequestVO saveRoleInfoRequestVO){
        logger.info("RoleOperateEndpoint:::saveRoleInfo:::保存角色信息接口接收到的参数为：" + saveRoleInfoRequestVO);
        if(null != saveRoleInfoRequestVO && !StringUtils.isBlank(saveRoleInfoRequestVO.getRiRoleName()) && !StringUtils.isBlank(saveRoleInfoRequestVO.getRiRoleDesc())){
            try {
                return roleInfoService.saveRoleInfo(saveRoleInfoRequestVO);
            } catch (Exception e) {
                logger.error("RoleOperateEndpoint:::saveRoleInfo:::保存角色信息接口异常",e);
            }
        }else {
            logger.error("RoleOperateEndpoint:::saveRoleInfo:::保存角色信息接口参数不合格!");
        }
        return "fail";
    }

    @PostMapping("/modify")
    public String modifyRoleInfo(@RequestBody RoleInfo roleInfo){
        logger.info("RoleOperateEndpoint:::modifyRoleInfo:::修改角色信息接口接收到的参数为：" + roleInfo);
        if(null != roleInfo && !StringUtils.isBlank(roleInfo.getRiUuid()) && !StringUtils.isBlank(roleInfo.getRiRoleName()) && !StringUtils.isBlank(roleInfo.getRiRoleDesc())){
            try {
                return roleInfoService.modifyRoleInfo(roleInfo);
            } catch (Exception e) {
                logger.error("RoleOperateEndpoint:::modifyRoleInfo:::修改角色信息接口异常",e);
            }
        }else {
            logger.error("RoleOperateEndpoint:::modifyRoleInfo:::修改角色信息接口参数不合格!");
        }
        return "fail";
    }

    @PostMapping("/saveAuthority")
    public String saveAuthority(@RequestBody SaveAuthorityRequestVO requestVO){
        logger.info("RoleOperateEndpoint:::saveAuthority:::接口接收到的参数为：" + requestVO);
        boolean flag = (null != requestVO && !StringUtils.isBlank(requestVO.getRiUuid()) && !StringUtils.isBlank(requestVO.getResourceType()) && null != requestVO.getServiceList() && null != requestVO.getUseScopeList() && !StringUtils.isBlank(requestVO.getOperateLevel()));
        if(flag){
            try {
                return roleInfoService.saveAuthority(requestVO.getRiUuid(),requestVO.getUseScopeList(),requestVO.getServiceList(),requestVO.getResourceType(),requestVO.getOperateLevel());
            } catch (Exception e) {
                logger.error("RoleOperateEndpoint:::saveAuthority:::接口异常",e);
            }
        }else {
            logger.error("RoleOperateEndpoint:::saveAuthority:::接口参数不合法!");
        }
        return "fail";
    }

    @PostMapping("/resourceTree/useScope")
    public List<ResourceTreeVO> getUseScopeTree(String riUuid, String usName, String redisId, String operateLevel){
        if(!StringUtils.isBlank(riUuid) && !StringUtils.isBlank(redisId) && !StringUtils.isBlank(operateLevel)){
            try {
                return roleInfoService.getUseScopeTree(riUuid,usName,redisId,operateLevel);
            } catch (Exception e) {
                logger.error("RoleOperateEndpoint:::getUseScopeTree:::获取资源树接口异常",e);
            }
        }else {
            logger.error("RoleOperateEndpoint:::getUseScopeTree:::获取资源树接口参数不合格!");
        }
        return null;
    }

    @PostMapping("/resourceTree/serviceResource")
    public List<ResourceTreeVO> getServiceResourceTree(String riUuid,String usName,String redisId,String operateLevel){
        if(!StringUtils.isBlank(riUuid) && !StringUtils.isBlank(redisId) && !StringUtils.isBlank(operateLevel)){
            try {
                return roleInfoService.getServiceResourceTree(riUuid,usName,redisId,operateLevel);
            } catch (Exception e) {
                logger.error("RoleOperateEndpoint:::getServiceResourceTree:::获取资源树接口异常",e);
            }
        }else {
            logger.error("RoleOperateEndpoint:::getServiceResourceTree:::获取资源树接口参数不合格!");
        }
        return null;
    }
}
