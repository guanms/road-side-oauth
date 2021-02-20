package com.tingbei.common.repository;/**
 * @Author:JXW
 * @Date:2021/2/19 21:28
 */

import com.tingbei.common.vo.role.ResourceTreeVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/19 21:28
 */
@Repository
public interface ResourceTreeRepository {
    List<ResourceTreeVO> queryUseScopeTreeByRiUuid(String riUuid, String usName, String operateLevel);

    List<ResourceTreeVO> querySuUseScopeTreeByRiUuid(String riUuid, String usName, String loginUiUuid, String operateLevel);

    List<ResourceTreeVO> queryServiceResourceTreeByRiUuid(String riUuid, String usName, String operateLevel);

    List<ResourceTreeVO> querySuServiceResourceTreeByRiUuid(String riUuid, String usName, String loginUiUuid, String operateLevel);
}
