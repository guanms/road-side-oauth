package com.tingbei.common.repository;

import com.tingbei.common.entity.UserExtendInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * UserExtendInfo 持久层基础操作
 */
@Repository
public interface UserExtendInfoRepository extends Mapper<UserExtendInfo> {
}
