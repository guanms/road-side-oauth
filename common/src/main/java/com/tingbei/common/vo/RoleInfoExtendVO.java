package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色信息返回扩展类
 * (UserRoleOperateEndpoint findUserRoleByUserName  接口返回类)
 */
@Data
public class RoleInfoExtendVO implements Serializable{
    private static final long serialVersionUID = 1079735792364192360L;
    private String riUuid;
    private String riRoleName;
    private String riRoleDesc;
    /**
     * 用户是否拥有此角色
     * 1 拥有  0 不用有
     */
    private String isOwner;
}
