package com.tingbei.common.repository;

import com.tingbei.common.vo.userresource.ExtendServiceResourceVo;
import com.tingbei.common.vo.userresource.ExtendUseScopeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 所有的资源列表持久层
 */
@Repository
public interface WholeResourceRepository {
    /**
     * 根据角色uuid从权限表中查询资源uuid
     * 再根据资源uuid从useScope中查询信息
     * @param operateLevel 操作权限
     * @param riUuidList 角色id列表
     * @return 返回
     */
    List<ExtendUseScopeVo> findScopeFromAuthAndTactices(@Param("operateLevel") String operateLevel, @Param("list") List<String> riUuidList);

    /**
     * 根据角色uuid从权限表中查询资源uuid
     * 再根据资源uuid从serviceResource中查询信息
     * @param operateLevel 操作权限
     * @param riUuidList 角色id列表
     * @return 返回
     */
    List<ExtendServiceResourceVo> findServiceResFromAuthAndTactices(@Param("operateLevel") String operateLevel, @Param("list") List<String> riUuidList);
}
