package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * authority 和 策略表抽取
 * Created by JJH on 2017/12/7.
 * @author JJH
 */
@Data
public class AuthorityVO implements Serializable{
    private static final long serialVersionUID = -1020510447299301235L;
    private String aUuid;
    private String riUuid;
    private String srUuid;
    /**
     * 资源类型 （服务资源 service  适用范围资源 scope）
     */
    private String srType;
    /**
     * 操作级别  （可展示 show\ 可选择select）
     */
    private String aOperateLevel;
    private String identityCode;
    private Integer areaCode;
}
