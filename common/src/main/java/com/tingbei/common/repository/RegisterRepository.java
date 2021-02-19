package com.tingbei.common.repository;

import com.tingbei.common.entity.UserInfo;
import com.tingbei.common.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RegisterRepository extends Mapper<UserInfo> {

    UserInfoVO findByLoginName(@Param("loginName") String loginName);

    void saveUserInfo(UserInfo userInfo);
}
