package com.tingbei.common.vo.role;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * role_info 映射实体
 * Created by JJH on 2017/12/6.
 * @author JJH
 */
@Data
public class SaveRoleInfoRequestVO implements Serializable{
    private static final long serialVersionUID = -256043432778290339L;

    private String riUuid;

    private String riRoleName;

    private String riRoleDesc;
    /**
     * 角色级别  （是否系统级别）
     * （1: 系统级别 —— 不可删除  0: 非系统级别 —— 可以删除）
     */

    private String riLevel;

    private String riArrtibute;

    private String riStatus;

    private String riCreateMan;

    private Date riCreateTime;

    private String identityCode;

    private Integer areaCode;

    /**
     * rediskey
     */
    private String redisId;
}
