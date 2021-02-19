package com.tingbei.common.repository;


import com.tingbei.common.entity.UserRoleInfo;
import com.tingbei.common.vo.RoleInfoExtendVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserRoleInfoRepository extends Mapper<UserRoleInfo> {
    /**
     * 根据用户uuid和角色描述查询角色信息(sp 服务提供者)
     * @param uiUuid 用户uuid
     * @param riRoleDesc 角色描述
     * @return 返回角色信息集合
     */
    List<RoleInfoExtendVO> findUserRoleByUserName(@Param("uiUuid") String uiUuid, @Param("riRoleDesc") String riRoleDesc);

    /**
     * 批量插入
     * @param list 批量数据
     * @return 返回插入行数
     */
    int insertBatch(@Param("list") List<UserRoleInfo> list);

    /**
     * 根据用户uuid和角色描述查询角色信息（su 服务使用者）
     * @param uiUuid 用户uuid
     * @param riRoleDesc 角色描述
     * @param createMan 创建人
     * @return 返回角色信息集合
     */
    List<RoleInfoExtendVO> findSuUserRoleByUserName(@Param("uiUuid") String uiUuid, @Param("riRoleDesc") String riRoleDesc, @Param("createMan") String createMan);

    /**
     * 根据用户uuid从用户角色表查询riUuid
     * @param uiUuid 角色id
     * @return 返回
     */
    List<String> findRoleUuidByUserUuid(@Param("uiUuid") String uiUuid);

    /**
     * 根据角色uuid从用户角色表查询用户uuid
     * @param riUuid 角色uuid
     * @return 返回
     */
    List<String> findUserUuidByRoleUuid(@Param("riUuid") String riUuid);
}
