package com.tingbei.common.vo.userresource;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务资源扩展类
 * Created by JJH on 2018/1/10.
 * @author JJH
 */
@Data
public class ExtendServiceResourceVo implements Serializable{
    private static final long serialVersionUID = 5842644970922991147L;
    /**
     * 资源uuid
     */
    private String srUuid;
    /**
     * 资源名称
     */
    private String srName;
    /**
     * 资源code
     */
    private String srCode;
    /**
     * 资源提供者
     */
    private String srProvider;
    /**
     * 资源状态
     */
    private String srStatus;
    /**
     * 资源描述
     */
    private String srDesc;
    /**
     * 资源版本
     */
    private String srVersion;
    /**
     * 资源有效时间
     */
    private Date srActiveDate;
    /**
     * 资源类型
     */
    private String srType;
    /**
     * 资源等级
     */
    private Long srLevel;
    /**
     * 资源url
     */
    private String srUrl;
    /**
     * 父结点uuid
     */
    private String srFatherUuid;
    /**
     * 创建时间
     */
    private Date srCreateTime;
    /**
     * 创建人
     */
    private String srCreateMan;
    /**
     * 操作级别
     */
    private String operateLevel;
    /**
     * 排序
     */
    private Long srSort;

    /**
     * 子节点
     */
    private List<ExtendServiceResourceVo> sonlist = new ArrayList<>();

    public void setEntity(ExtendServiceResourceVo extendServiceResourceVo){
        setSrUuid(extendServiceResourceVo.getSrUuid());
        setSrName(extendServiceResourceVo.getSrName());
        setSrCode(extendServiceResourceVo.getSrCode());
        setSrProvider(extendServiceResourceVo.getSrProvider());
        setSrStatus(extendServiceResourceVo.getSrStatus());
        setSrDesc(extendServiceResourceVo.getSrDesc());
        setSrVersion(extendServiceResourceVo.getSrVersion());
        setSrActiveDate(extendServiceResourceVo.getSrActiveDate());
        setSrType(extendServiceResourceVo.getSrType());
        setSrLevel(extendServiceResourceVo.getSrLevel());
        setSrUrl(extendServiceResourceVo.getSrUrl());
        setSrFatherUuid(extendServiceResourceVo.getSrFatherUuid());
        setSrCreateTime(extendServiceResourceVo.getSrCreateTime());
        setSrCreateMan(extendServiceResourceVo.getSrCreateMan());
        setOperateLevel(extendServiceResourceVo.getOperateLevel());
    }
}
