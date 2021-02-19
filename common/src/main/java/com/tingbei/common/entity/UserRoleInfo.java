package com.tingbei.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * user_role_info 映射表结构
 * Created by JJH on 2017/12/7.
 * @author JJH
 */
@Data
public class UserRoleInfo {
    @Id
    @GeneratedValue(generator = "UUID")
    private String uriUuid;
    @Column
    private String uiUuid;
    @Column
    private String riUuid;
    @Column
    private String identityCode;
    @Column
    private Integer areaCode;
}
