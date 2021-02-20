package com.tingbei.common.repository;

import com.tingbei.common.entity.UserInfo;
import com.tingbei.common.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserInfoRepository extends Mapper<UserInfo> {

    /**
     * 精确查询
     * @param loginName 登录名
     * @param identityCode 身份编码
     * @return 返回UserInfoVO
     */
    UserInfoVO queryWholeUserInfo(@Param("loginName") String loginName, @Param("identityCode") String identityCode);

    /**
     * 模糊查询有效用户
     * (status=normal)
     * 用户角色功能查询的用户
     * @param userName 用户名模糊查询
     * @param attribute 属性
     * @param createMan 创建人
     * @return 返回
     */
    List<UserInfoVO> queryNormalUserInfoByUserNameFuzzy(@Param("userName") String userName, @Param("attribute") String attribute, @Param("createMan") String createMan);

    /**
     * 根据usCode查询拥有支付通知权限的openId
     * @param usCode 资源code
     * @param srName 资源名称
     * @return 返回openId集合
     */
    List<String> queryOpenIdHavePayMessageByUsCode(@Param("usCode") String usCode,@Param("srName") String srName);

    /**
     * 模糊查询未审核用户
     * @param userName 用户名模糊查询
     * @return 返回
     */
    List<UserInfoVO> queryUnauditedUserInfoByUserNameFuzzy(@Param("userName") String userName);

    /**
     * 模糊查询已审核用户
     * (status 排除unaudited 和 deleted)
     * @param userName 用户名模糊查询
     * @param attribute 用户属性 （sp 不带创建人 su 带创建人）
     * @param createMan 创建人
     * @return 返回
     */
    List<UserInfoVO> queryAuditedUserInfoByUserNameFuzzy(@Param("userName") String userName,@Param("attribute") String attribute,@Param("createMan") String createMan);


    List<UserInfo> findUserInfosByUuids(List<String> list);


    /**
     * 检查userName是否重名
     * @param userName 用户名
     * @param uiUuid 主键
     * @return 返回
     */
    List<UserInfo> checkUserNameCanUse(@Param("userName") String userName,@Param("uiUuid") String uiUuid);

    /**
     * 检查loginName是否重复
     * @param loginName 名
     * @param uiUuid 建
     * @return 返回
     */
    List<UserInfo> checkLoginNameCanUse(@Param("loginName") String loginName,@Param("uiUuid") String uiUuid);


}
