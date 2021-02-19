package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户和角色关联关系
 * 一个用户对应多个角色
 */
@Data
public class UserAndRoleVO implements Serializable{
    private static final long serialVersionUID = -8134872478085300817L;
    private UserInfoVO userInfo;
    private List<RoleAndResourceVO> roleAndResourceList;
}
