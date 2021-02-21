package com.tingbei.common.repository;/**
 * @Author:JXW
 * @Date:2021/2/19 21:28
 */

import com.tingbei.common.vo.role.ResourceTreeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/19 21:28
 */
@Repository
public interface ResourceTreeRepository {
    List<ResourceTreeVO> queryUseScopeTreeByRiUuid(@Param("riUuid") String riUuid,@Param("usName") String usName,@Param("operateLevel") String operateLevel);

    List<ResourceTreeVO> querySuUseScopeTreeByRiUuid(@Param("riUuid") String riUuid,@Param("usName") String usName,@Param("loginUiUuid") String loginUiUuid,@Param("operateLevel") String operateLevel);

    List<ResourceTreeVO> queryServiceResourceTreeByRiUuid(@Param("riUuid") String riUuid, @Param("srName") String srName, @Param("operateLevel") String operateLevel);

    List<ResourceTreeVO> querySuServiceResourceTreeByRiUuid(@Param("riUuid") String riUuid,@Param("srName") String srName,@Param("loginUiUuid") String loginUiUuid,@Param("operateLevel") String operateLevel);
}
