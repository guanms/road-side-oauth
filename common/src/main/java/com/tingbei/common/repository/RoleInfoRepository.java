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

    List<RoleInfo> selectByRoleDesc(@Param("riRoleDesc") String riRoleDesc,@Param("attribute") String userAttribute,@Param("createMan") String loginName);

    RoleInfoVO queryInfoFromRoleAndTactics(String riUuid);
}
