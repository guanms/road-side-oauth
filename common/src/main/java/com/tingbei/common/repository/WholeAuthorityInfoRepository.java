package com.tingbei.common.repository;


import com.tingbei.common.vo.AuthorityVO;
import com.tingbei.common.vo.ServiceResourceVO;
import com.tingbei.common.vo.UseScopeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 完整的权限信息
 * 从权限表和权限策略表查询
 */
@Repository
public interface WholeAuthorityInfoRepository {

    List<AuthorityVO> queryInfoFromAuthAndTactics(@Param("riUuid") String riUuid);

    /**
     * 查询使用范围资源从权限表
     * @param riUuid 角色信息
     * @return 返回
     */
    List<UseScopeVO> queryUsInfoFromAuthByRiUuid(@Param("riUuid") String riUuid);

    /**
     * 从权限表查询服务资源信息
     * @param riUuid 角色信息
     * @return 返回
     */
    List<ServiceResourceVO> querySrInfoFromAuthByRiUuid(@Param("riUuid") String riUuid);
}
