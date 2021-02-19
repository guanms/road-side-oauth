package com.tingbei.common.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * authority 表映射
 * Created by JJH on 2017/12/7.
 * @author JJH
 */
@Data
public class Authority implements Serializable{
    private static final long serialVersionUID = -1020510447299301235L;
    @Id
    @GeneratedValue(generator = "UUID")
    private String aUuid;
    @Column
    private String riUuid;
    @Column
    private String srUuid;
    /**
     * 资源类型 （服务资源 service  适用范围资源 scope）
     */
    @Column
    private String srType;
    /**
     * 操作级别  （可展示 show\ 可选择select）
     */
    @Column
    private String aOperateLevel;
    @Column
    private String identityCode;
    @Column
    private Integer areaCode;
}
