package com.tingbei.common.repository;

import com.tingbei.common.entity.OauthUsers;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


@Repository
public interface OauthUsersRepository extends Mapper<OauthUsers>{
}
