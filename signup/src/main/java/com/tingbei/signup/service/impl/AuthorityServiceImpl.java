package com.tingbei.signup.service.impl;

import com.tingbei.common.entity.UserRoleInfo;
import com.tingbei.common.repository.*;
import com.tingbei.common.vo.*;
import com.tingbei.common.vo.userresource.ExtendServiceResourceVo;
import com.tingbei.common.vo.userresource.ExtendUseScopeVo;
import com.tingbei.common.vo.userresource.UserAndResourceVO;
import com.tingbei.signup.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限服务实现qwqwqwqwqwqwqwqwq
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private UserInfoRepository wholeUserInfoRepository;
    private UserRoleInfoRepository userRoleInfoRepository;
    private WholeAuthorityInfoRepository wholeAuthorityInfoRepository;
    private RoleInfoRepository roleInfoRepository;
    private WholeResourceRepository wholeResourceRepository;

    @Autowired
    public AuthorityServiceImpl(UserInfoRepository wholeUserInfoRepository, UserRoleInfoRepository userRoleInfoRepository, WholeAuthorityInfoRepository wholeAuthorityInfoRepository,
                                RoleInfoRepository roleInfoRepository, WholeResourceRepository wholeResourceRepository){
        this.wholeUserInfoRepository = wholeUserInfoRepository;
        this.userRoleInfoRepository = userRoleInfoRepository;
        this.wholeAuthorityInfoRepository = wholeAuthorityInfoRepository;
        this.roleInfoRepository = roleInfoRepository;
        this.wholeResourceRepository = wholeResourceRepository;
    }


    @Override
    public UserAndRoleVO queryUserAllRoleResource(String loginName, String identityCode) {
        UserAndRoleVO result = new UserAndRoleVO();
        UserInfoVO userInfoVO = this.wholeUserInfoRepository.queryWholeUserInfo(loginName,identityCode);
        if(null != userInfoVO){
            UserRoleInfo param = new UserRoleInfo();
            param.setUiUuid(userInfoVO.getUiUuid());
            List<UserRoleInfo> roleIdList = this.userRoleInfoRepository.select(param);
            List<RoleAndResourceVO> roleAndResourceList = new ArrayList<>();
            for (UserRoleInfo userRoleInfo:roleIdList) {
                // 查询roleInfo角色信息
                RoleInfoVO roleInfoVO = this.roleInfoRepository.queryInfoFromRoleAndTactics(userRoleInfo.getRiUuid());
                // 根据角色id去查询权限表和权限策略表
                List<UseScopeVO> scopeList = this.wholeAuthorityInfoRepository.queryUsInfoFromAuthByRiUuid(userRoleInfo.getRiUuid());
                List<ServiceResourceVO> serviceList = this.wholeAuthorityInfoRepository.querySrInfoFromAuthByRiUuid(userRoleInfo.getRiUuid());
                // 组装 RoleAndResourceVO
                RoleAndResourceVO roleAndResourceVO = new RoleAndResourceVO();
                roleAndResourceVO.setRoleInfoVO(roleInfoVO);
                roleAndResourceVO.setScopeList(scopeList);
                roleAndResourceVO.setServiceList(serviceList);
                roleAndResourceList.add(roleAndResourceVO);
            }
            result.setUserInfo(userInfoVO);
            result.setRoleAndResourceList(roleAndResourceList);
        }
        return result;
    }

    @Override
    @Cacheable(value = "userAllResource",keyGenerator = "wiselyKeyGenerator")
    public UserAndResourceVO queryUserAllResource(String loginName, String... identityCode) {
        UserAndResourceVO userAndResourceVO = new UserAndResourceVO();
        UserInfoVO userInfoVO ;
        if(null != identityCode && identityCode.length == 1){
            userInfoVO = this.wholeUserInfoRepository.queryWholeUserInfo(loginName,identityCode[0]);
        }else {
            userInfoVO = this.wholeUserInfoRepository.queryWholeUserInfo(loginName,null);
        }
        userAndResourceVO.setUserInfo(userInfoVO);
        if(null != userInfoVO){
            List<String> riUuidList = this.userRoleInfoRepository.findRoleUuidByUserUuid(userInfoVO.getUiUuid());
            if(null != riUuidList && riUuidList.size() > 0){
                // 查询展示的使用范围资源
                List<ExtendUseScopeVo> showScopeList = this.wholeResourceRepository.findScopeFromAuthAndTactices("show",riUuidList);
                showScopeList = useScopeListToTreeList(showScopeList);
                userAndResourceVO.setShowScopeList(showScopeList);
                // 查询可选择的使用返回资源
                List<ExtendUseScopeVo> selectScopeList = this.wholeResourceRepository.findScopeFromAuthAndTactices("select",riUuidList);
                selectScopeList = useScopeListToTreeList(selectScopeList);
                userAndResourceVO.setSelectScopeList(selectScopeList);
                // 查询展示的服务资源
                List<ExtendServiceResourceVo> showSrList = this.wholeResourceRepository.findServiceResFromAuthAndTactices("show",riUuidList);
                showSrList = srListToTreeList(showSrList);
                userAndResourceVO.setShowSrList(showSrList);
                // 查询操作的服务资源
                List<ExtendServiceResourceVo> selectSrList = this.wholeResourceRepository.findServiceResFromAuthAndTactices("select",riUuidList);
                selectSrList = srListToTreeList(selectSrList);
                userAndResourceVO.setSelectSrList(selectSrList);
            }
        }
        return userAndResourceVO;
    }

    @Override
    @CachePut(value = "userAllResource",keyGenerator = "wiselyKeyGenerator")
    public UserAndResourceVO updateCacheQueryUserAllResource(String loginName, String... identityCode) {
        return queryUserAllResource(loginName,identityCode);
    }


    private List<ExtendUseScopeVo> useScopeListToTreeList(List<ExtendUseScopeVo> extendUseScopeVos){
        Map<String,ExtendUseScopeVo> maps = new HashMap<>(10);
        List<ExtendUseScopeVo> list = new ArrayList<>();
        Map<String,String> haveSonNoFather = new HashMap<>(10);
        for (ExtendUseScopeVo extendUseScopeVo:extendUseScopeVos) {
            addUseScopeTreeNode(extendUseScopeVo,maps,list,haveSonNoFather);
        }
        // 将haveSonNoFather中的结点最后放入list中
        for (String noFatherString: haveSonNoFather.keySet()){
            list.add(maps.get(noFatherString));
        }
        return list;
    }

    private List<ExtendServiceResourceVo> srListToTreeList(List<ExtendServiceResourceVo> extendServiceResourceVos){
        Map<String,ExtendServiceResourceVo> maps = new HashMap<>(10);
        Map<String,String> haveSonNoFather = new HashMap<>(10);
        List<ExtendServiceResourceVo> list = new ArrayList<>();
        for (ExtendServiceResourceVo extendServiceResourceVo:extendServiceResourceVos) {
            addServiceResourceTreeNode(extendServiceResourceVo,maps,list,haveSonNoFather);
        }
        // 将haveSonNoFather中的结点最后放入list中
        for (String noFatherString: haveSonNoFather.keySet()){
            list.add(maps.get(noFatherString));
        }
        return list;
    }

    /**
     * 将useScope放入树形父子结构
     * 所有结点都放入maps中连接
     * 顶层结点时，将maps中对应的value放入list
     * 结点有上层结构的，将自己放入sonList，结点有下层结构的，在maps中等待
     * 先出现顶层父节点，则在maps中等待被儿子寻找，并插入list；
     * 先出现子节点，则将fatheruuid写入父结点属性，自己进入sonList
     * @param extendUseScopeVo 一个使用范围资源信息（可能是儿子可能是爸爸）
     * @param maps 临时存储范围资源信息的map
     * @param list 最终返回的树形父子结构
     * @param haveSonNoFather 存放是孩子没找到父节点的（考虑只勾选了一个子结点资源的情况）
     */
    private void addUseScopeTreeNode(ExtendUseScopeVo extendUseScopeVo,Map<String,ExtendUseScopeVo> maps,List<ExtendUseScopeVo> list,Map<String,String> haveSonNoFather){
        if(maps.containsKey(extendUseScopeVo.getUsUuid()) && null == maps.get(extendUseScopeVo.getUsUuid()).getUsUuid()){
            // maps中有这个使用范围资源，但是信息不完整（即先便利了子节点，将父节点添入了父亲层，儿子列表里有孩子）
            maps.get(extendUseScopeVo.getUsUuid()).setEntity(extendUseScopeVo);
            haveSonNoFather.remove(extendUseScopeVo.getUsUuid());
        }else{
            maps.put(extendUseScopeVo.getUsUuid(), extendUseScopeVo);
        }
        if(null != extendUseScopeVo.getUsFatherUuid()){
            // 表明此结点为孩子结点
            if(maps.containsKey(extendUseScopeVo.getUsFatherUuid())){
                // 查询maps中有没有父亲，有则加入父亲的sonList
                maps.get(extendUseScopeVo.getUsFatherUuid()).getSonList().add(maps.get(extendUseScopeVo.getUsUuid()));
            }else {
                // maps中没有父亲，那么将fatherUuid填入父亲属性中，自己加入sonList
                ExtendUseScopeVo father = new ExtendUseScopeVo();
                father.getSonList().add(maps.get(extendUseScopeVo.getUsUuid()));
                maps.put(extendUseScopeVo.getUsFatherUuid(),father);
                haveSonNoFather.put(extendUseScopeVo.getUsFatherUuid(),"");
            }
        }else {
            // 是顶层结点，那么直接放入list
            list.add(maps.get(extendUseScopeVo.getUsUuid()));
        }
    }


    private void addServiceResourceTreeNode(ExtendServiceResourceVo extendServiceResourceVo,Map<String,ExtendServiceResourceVo> maps,List<ExtendServiceResourceVo> list,Map<String,String> haveSonNoFather){
        if(maps.containsKey(extendServiceResourceVo.getSrUuid()) && null == maps.get(extendServiceResourceVo.getSrUuid()).getSrUuid()){
            maps.get(extendServiceResourceVo.getSrUuid()).setEntity(extendServiceResourceVo);
            haveSonNoFather.remove(extendServiceResourceVo.getSrUuid());
        }else {
            maps.put(extendServiceResourceVo.getSrUuid(),extendServiceResourceVo);
        }
        if(null == extendServiceResourceVo.getSrFatherUuid()){
            list.add(maps.get(extendServiceResourceVo.getSrUuid()));
        }else {
            if(maps.containsKey(extendServiceResourceVo.getSrFatherUuid())){
                maps.get(extendServiceResourceVo.getSrFatherUuid()).getSonlist().add(maps.get(extendServiceResourceVo.getSrUuid()));
            }else {
                ExtendServiceResourceVo father = new ExtendServiceResourceVo();
                father.getSonlist().add(maps.get(extendServiceResourceVo.getSrUuid()));
                maps.put(extendServiceResourceVo.getSrFatherUuid(),father);
                haveSonNoFather.put(extendServiceResourceVo.getSrFatherUuid(),"");
            }
        }
    }
}
