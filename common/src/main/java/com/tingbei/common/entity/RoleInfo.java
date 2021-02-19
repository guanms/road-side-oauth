package com.tingbei.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * role_info 映射实体
 * Created by JJH on 2017/12/6.
 * @author JJH
 */
@Data
public class RoleInfo implements Serializable{
    private static final long serialVersionUID = -256043432778290339L;
    @Id
    @GeneratedValue(generator = "UUID")
    private String riUuid;
    @Column
    private String riRoleName;
    @Column
    private String riRoleDesc;
    /**
     * 角色级别  （是否系统级别）
     * （1: 系统级别 —— 不可删除  0: 非系统级别 —— 可以删除）
     */
    @Column
    private String riLevel;
    @Column
    private String riArrtibute;
    @Column
    private String riStatus;
    @Column
    private String riCreateMan;
    @Column
    private Date riCreateTime;
    @Column
    private String identityCode;
    @Column
    private Integer areaCode;
}
