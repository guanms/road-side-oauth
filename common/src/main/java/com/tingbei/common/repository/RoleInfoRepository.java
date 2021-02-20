package com.tingbei.common.repository;/**
 * @Author:JXW
 * @Date:2021/2/19 16:46
 */

import com.tingbei.common.entity.RoleInfo;
import com.tingbei.common.vo.RoleInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/19 16:46
 */
@Repository
public interface RoleInfoRepository extends Mapper<RoleInfo> {

    /**
     * 从角色表和角色策略表查询角色信息
     * @param riUuid 角色序列
     * @return 返回RoleInfoVO
     */
    RoleInfoVO queryInfoFromRoleAndTactics(@Param("riUuid") String riUuid);

    List<RoleInfo> selectByRoleDesc(String riRoleDesc, String userAttribute, String loginName);
}
