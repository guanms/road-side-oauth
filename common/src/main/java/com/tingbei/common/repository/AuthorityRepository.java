package com.tingbei.common.repository;/**
 * @Author:JXW
 * @Date:2021/2/19 17:24
 */

import com.tingbei.common.entity.Authority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *@Author:JXW
 *@Date:2021/2/19 17:24
 */
@Repository
public interface AuthorityRepository extends Mapper<Authority> {

    int insertBatch(@Param("list") List<Authority> list);
}
