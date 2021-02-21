package com.tingbei.signup.service;/**
 * @Author:JXW
 * @Date:2021/2/19 16:43
 */

import com.github.pagehelper.PageInfo;
import com.tingbei.common.entity.RoleInfo;
import com.tingbei.common.vo.role.ResourceTreeVO;
import com.tingbei.common.vo.role.RolePageQueryParam;
import com.tingbei.common.vo.role.SaveRoleInfoRequestVO;

import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/19 16:43
 */
public interface RoleInfoService {

    PageInfo<RoleInfo> pageQueryRoleInfo(RolePageQueryParam pageParam);

    String deleteRoleInfo(String riUuid);

    String saveRoleInfo(SaveRoleInfoRequestVO saveRoleInfoRequestVO);

    String modifyRoleInfo(RoleInfo roleInfo);

    String saveAuthority(String riUuid, List<String> useScopeList, List<String> serviceList, String resourceType, String operateLevel);

    List<ResourceTreeVO> getUseScopeTree(String riUuid, String usName, String redisId, String operateLevel);

    List<ResourceTreeVO> getServiceResourceTree(String riUuid, String usName, String redisId, String operateLevel);
}
