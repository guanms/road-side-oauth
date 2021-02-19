package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 角色信息表中抽取的给用户返回的角色信息
 */
@Data
public class RoleInfoVO implements Serializable{
    private static final long serialVersionUID = 3372907326731167654L;
    private String riUuid;
    private String roleName;
    private String riLevel;
    private String riArrtibute;
}
