package com.tingbei.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * user_extend_info 对应持久层
 */
@Data
public class UserExtendInfo implements Serializable{
    private static final long serialVersionUID = 4861412430877897380L;
    @Id
    @GeneratedValue(generator="UUID")
    private String ueiUuid;
    @Column
    private String uiUuid;
    @Column
    private String ueiSpecialMark;
    @Column
    private String ueiType;
    @Column
    private String identityCode;
    @Column
    private Integer areaCode;
}
