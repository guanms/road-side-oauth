package com.tingbei.oauth.service.impl;/**
 * @Author:JXW
 * @Date:2021/2/19 16:43
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tingbei.common.constant.CommonStatus;
import com.tingbei.common.constant.RedisPrefix;
import com.tingbei.common.constant.ResourceType;
import com.tingbei.common.entity.Authority;
import com.tingbei.common.entity.RoleInfo;
import com.tingbei.common.entity.UserRoleInfo;
import com.tingbei.common.repository.AuthorityRepository;
import com.tingbei.common.repository.ResourceTreeRepository;
import com.tingbei.common.repository.RoleInfoRepository;
import com.tingbei.common.repository.UserRoleInfoRepository;
import com.tingbei.common.util.CommonUtil;
import com.tingbei.common.vo.TokenAndResourceVo;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.common.vo.role.ResourceTreeVO;
import com.tingbei.common.vo.role.RolePageQueryParam;
import com.tingbei.common.vo.role.SaveRoleInfoRequestVO;
import com.tingbei.oauth.service.RoleInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/19 16:43
 */
@Service
public class RoleInfoServiceImpl implements RoleInfoService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RoleInfoRepository roleInfoRepository;

    @Autowired
    private UserRoleInfoRepository userRoleInfoRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private ResourceTreeRepository resourceTreeRepository;

    @Override
    public PageInfo<RoleInfo> pageQueryRoleInfo(RolePageQueryParam pageParam) {
        String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + pageParam.getRedisId();
        TokenAndResourceVo tokenAndResourceVo = (TokenAndResourceVo) redisTemplate.opsForValue().get(redisKey);
        if(null != tokenAndResourceVo && null != tokenAndResourceVo.getUserAndResourceVO() && null != tokenAndResourceVo.getUserAndResourceVO().getUserInfo()){
            UserInfoVO userInfoVO = tokenAndResourceVo.getUserAndResourceVO().getUserInfo();
            PageHelper.startPage(pageParam.getPageNumber(),pageParam.getPageSize());
            List<RoleInfo> list = roleInfoRepository.selectByRoleDesc(pageParam.getRiRoleDesc(),userInfoVO.getUserAttribute(),userInfoVO.getLoginName());
            return new PageInfo<>(list);
        }else {
            logger.error("RoleOperateServiceImpl:::pageQueryRoleInfo:::redis中查询不到用户信息");
            return null;
        }
    }

    @Override
    public String deleteRoleInfo(String riUuid) {
        int lines = roleInfoRepository.deleteByPrimaryKey(riUuid);
        if(lines > 0){
            UserRoleInfo delUr = new UserRoleInfo();
            delUr.setRiUuid(riUuid);
            userRoleInfoRepository.delete(delUr);
            Authority delAu = new Authority();
            delAu.setRiUuid(riUuid);
            authorityRepository.delete(delAu);
            return "success";
        }else {
            return "fail";
        }
    }

    @Override
    public String saveRoleInfo(SaveRoleInfoRequestVO saveRoleInfoRequestVO) {
        String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + saveRoleInfoRequestVO.getRedisId();
        TokenAndResourceVo tokenAndResourceVo = (TokenAndResourceVo) redisTemplate.opsForValue().get(redisKey);
        if(null != tokenAndResourceVo && null != tokenAndResourceVo.getUserAndResourceVO() && null != tokenAndResourceVo.getUserAndResourceVO().getUserInfo()) {
            UserInfoVO createMan = tokenAndResourceVo.getUserAndResourceVO().getUserInfo();

            RoleInfo roleInfo = new RoleInfo();
            BeanUtils.copyProperties(saveRoleInfoRequestVO,roleInfo);

            // 如果描述信息是一样的，则默认为同一性质的角色，不予添加
            roleInfo.setRiUuid(commonUtil.generateUUID());
            roleInfo.setRiStatus(CommonStatus.NORAML.getStatus());
            // 创建人为系统
            roleInfo.setRiCreateMan(createMan.getLoginName());
            roleInfo.setRiCreateTime(new Date());
            int lines = roleInfoRepository.insertSelective(roleInfo);
            if(lines > 0){
                return "success";
            }else {
                return "fail";
            }
        }else{
            return "fail";
        }
    }

    @Override
    public String modifyRoleInfo(RoleInfo roleInfo) {
        RoleInfo old = roleInfoRepository.selectByPrimaryKey(roleInfo.getRiUuid());
        if(null != old){
            old.setRiRoleName(roleInfo.getRiRoleName());
            old.setRiRoleDesc(roleInfo.getRiRoleDesc());
            old.setRiLevel(roleInfo.getRiLevel());
            old.setRiCreateTime(new Date());
            roleInfoRepository.updateByPrimaryKeySelective(old);
            return "success";
        }else {
            logger.error("修改角色信息，角色信息主键不存在！");
            return "fail";
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String saveAuthority(String riUuid, List<String> useScopeList, List<String> serviceList, String resourceType, String operateLevel) {
        int lines = 0;
        // 删除此角色旧权限
        Authority del = new Authority();
        del.setRiUuid(riUuid);
        if(resourceType.equals(ResourceType.SCOPE.getType())){
            del.setSrType(ResourceType.SCOPE.getType());
        }else {
            del.setSrType(ResourceType.SERVICE.getType());
        }
        if(operateLevel.equals(ResourceType.SHOW.getType())){
            del.setAOperateLevel(ResourceType.SHOW.getType());
        }else {
            del.setAOperateLevel(ResourceType.SELECT.getType());
        }
        lines += authorityRepository.delete(del);
        List<Authority> list = new ArrayList<>();
        // 1. 先循环整理使用范围资源
        for (String useScopeUuid:useScopeList) {
            Authority authority = new Authority();
            authority.setAUuid(this.commonUtil.generateUUID());
            authority.setRiUuid(riUuid);
            authority.setSrUuid(useScopeUuid);
            authority.setSrType(ResourceType.SCOPE.getType());
            authority.setAOperateLevel(operateLevel);
            // 身份编码和区域编码未知
            list.add(authority);
        }
        // 2. 循环整理服务资源
        for (String serviceUuid:serviceList) {
            Authority authority = new Authority();
            authority.setAUuid(this.commonUtil.generateUUID());
            authority.setRiUuid(riUuid);
            authority.setSrUuid(serviceUuid);
            authority.setSrType(ResourceType.SERVICE.getType());
            authority.setAOperateLevel(operateLevel);
            // 身份编码和区域编码未知
            list.add(authority);
        }
        if (list.size() > 0) {
            lines += authorityRepository.insertBatch(list);
        }
        if(lines > 0){
            upUserResourceCacheService.upUserResCacheByRiuuidOrUiuuid(riUuid,null);
            return "success";
        }
        return "fail";
    }

    /**
     * 根据角色id查询所有资源，拥有的勾选，未拥有的不勾选
     * 备注： 对应zTree控件。 pUuid=0为根节点， 1为使用资源结点  2为服务资源结点
     * @param riUuid 角色唯一序列
     * @param usName 使用范围资源名称
     * @param redisId 缓存id
     * @return 返回 ResourceTreeVO 集合
     * @throws Exception 异常
     */
    @Override
    public List<ResourceTreeVO> getUseScopeTree(String riUuid, String usName, String redisId, String operateLevel) {
        // 初始化使用资源结点
        ResourceTreeVO useScopeRoot = new ResourceTreeVO();
        useScopeRoot.setUuid("1");
        useScopeRoot.setPuuid("0");
        useScopeRoot.setName("使用范围资源");
        useScopeRoot.setChecked(false);
        List<ResourceTreeVO> list = new ArrayList<>();
        list.add(useScopeRoot);
        String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + redisId;
        TokenAndResourceVo tokenAndResourceVo = (TokenAndResourceVo) this.redisTemplate.opsForValue().get(redisKey);
        if(null != tokenAndResourceVo && null != tokenAndResourceVo.getUserAndResourceVO() && null != tokenAndResourceVo.getUserAndResourceVO().getUserInfo() &&
                !StringUtils.isBlank(tokenAndResourceVo.getUserAndResourceVO().getUserInfo().getUserAttribute())){
            String attribute = tokenAndResourceVo.getUserAndResourceVO().getUserInfo().getUserAttribute();
            String loginUiUuid = tokenAndResourceVo.getUserAndResourceVO().getUserInfo().getUiUuid();
            if("SP".equals(attribute)){
                // SP 服务提供者查询所有资源
                List<ResourceTreeVO> sonUseScopeList = resourceTreeRepository.queryUseScopeTreeByRiUuid(riUuid,usName,operateLevel);
                list.addAll(sonUseScopeList);
            }else {
                // SU 服务使用者查询拥有权限中的可操作资源
                List<ResourceTreeVO> sonUseScopeList = resourceTreeRepository.querySuUseScopeTreeByRiUuid(riUuid,usName,loginUiUuid,operateLevel);
                list.addAll(sonUseScopeList);
            }
        }else {
            logger.error("redis中没有缓存登录用户信息，请检查！");
        }
        return list;
    }

    /**
     * 根据角色id查询所有资源，拥有的勾选，未拥有的不勾选
     * 备注： 对应zTree控件。 pUuid=0为根节点， 1为使用资源结点  2为服务资源结点
     * @param riUuid 角色id
     * @param usName 资源名称
     * @param redisId 缓存id
     * @param operateLevel 操作级别
     * @return 返回 ResourceTreeVO 集合
     * @throws Exception 异常
     */
    @Override
    public List<ResourceTreeVO> getServiceResourceTree(String riUuid, String usName, String redisId, String operateLevel) {
        // 初始化服务资源结点
        ResourceTreeVO serviceResourceRoot = new ResourceTreeVO();
        serviceResourceRoot.setUuid("2");
        serviceResourceRoot.setPuuid("0");
        serviceResourceRoot.setName("服务资源");
        serviceResourceRoot.setChecked(false);
        List<ResourceTreeVO> list = new ArrayList<>();
        list.add(serviceResourceRoot);
        String redisKey = RedisPrefix.PAGE_LOGIN_USERINFO.getPrefix() + redisId;
        TokenAndResourceVo tokenAndResourceVo = (TokenAndResourceVo) this.redisTemplate.opsForValue().get(redisKey);
        if(null != tokenAndResourceVo && null != tokenAndResourceVo.getUserAndResourceVO() && null != tokenAndResourceVo.getUserAndResourceVO().getUserInfo() &&
                !StringUtils.isBlank(tokenAndResourceVo.getUserAndResourceVO().getUserInfo().getUserAttribute())){
            String attribute = tokenAndResourceVo.getUserAndResourceVO().getUserInfo().getUserAttribute();
            String loginUiUuid = tokenAndResourceVo.getUserAndResourceVO().getUserInfo().getUiUuid();
            if("SP".equals(attribute)){
                // SP 服务提供者查询所有资源
                List<ResourceTreeVO> serviceResourceList = resourceTreeRepository.queryServiceResourceTreeByRiUuid(riUuid,usName,operateLevel);
                list.addAll(serviceResourceList);
            }else {
                // SU 服务使用者查询拥有权限中的可操作资源
                List<ResourceTreeVO> serviceResourceList = resourceTreeRepository.querySuServiceResourceTreeByRiUuid(riUuid,usName,loginUiUuid,operateLevel);
                list.addAll(serviceResourceList);
            }
        }else {
            logger.error("redis中没有缓存登录用户信息，请检查！");
        }
        return list;
    }
}
