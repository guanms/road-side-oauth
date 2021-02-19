package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 每个角色对应的服务资源和使用范围资源
 * 一个角色信息下有多个服务资源和使用范围
 */
@Data
public class RoleAndResourceVO implements Serializable{
    private static final long serialVersionUID = -7708319030025914735L;
    private RoleInfoVO roleInfoVO;
    private List<ServiceResourceVO> serviceList;
    private List<UseScopeVO> scopeList;
}
