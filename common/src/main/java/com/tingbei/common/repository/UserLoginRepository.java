package com.tingbei.common.repository;/**
 * @Author:JXW
 * @Date:2021/2/19 13:55
 */

import com.tingbei.common.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *@Author:JXW
 *@Date:2021/2/19 13:55
 */
@Repository
public interface UserLoginRepository {

    UserInfoVO findByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);
}
