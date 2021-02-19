package com.tingbei.common.vo.userresource;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 适用范围资源扩展类
 * Created by JJH on 2018/1/10.
 * @author JJH
 */
@Data
public class ExtendUseScopeVo implements Serializable{
    private static final long serialVersionUID = 1867092083373991263L;
    /**
     * 资源uuid
     */
    private String usUuid;
    /**
     * 资源名称
     */
    private String usName;
    /**
     * 资源code
     */
    private String usCode;
    /**
     * 资源描述
     */
    private String usDesc;
    /**
     * 资源提供者
     */
    private String usProvider;
    /**
     * 资源类型
     */
    private String usType;
    /**
     * 资源级别
     */
    private Long usLevel;
    /**
     * 父资源uuid
     */
    private String usFatherUuid;
    /**
     * 资源状态
     */
    private String usStatus;
    /**
     * 资源创建时间
     */
    private Date usCreateTime;
    /**
     * 资源创建人
     */
    private String usCreateMan;
    /**
     * 资源操作等级
     */
    private String operateLevel;
    /**
     * 儿子列表
     * 需要申请加初始化,防止getSonList得到null
     */
    private List<ExtendUseScopeVo> sonList = new ArrayList<>();

    /**
     * 设置除usUuid字段外的所有属性
     * （组装父子结构时使用）
     * @param extendUseScopeVo 参数
     */
    public void setEntity(ExtendUseScopeVo extendUseScopeVo){
        setUsUuid(extendUseScopeVo.getUsUuid());
        setUsName(extendUseScopeVo.getUsName());
        setUsCode(extendUseScopeVo.getUsCode());
        setUsDesc(extendUseScopeVo.getUsDesc());
        setUsProvider(extendUseScopeVo.getUsProvider());
        setUsType(extendUseScopeVo.getUsType());
        setUsLevel(extendUseScopeVo.getUsLevel());
        setUsFatherUuid(extendUseScopeVo.getUsFatherUuid());
        setUsStatus(extendUseScopeVo.getUsStatus());
        setUsCreateTime(extendUseScopeVo.getUsCreateTime());
        setUsCreateMan(extendUseScopeVo.getUsCreateMan());
        setOperateLevel(extendUseScopeVo.getOperateLevel());
    }
}
