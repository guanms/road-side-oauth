package com.tingbei.common.vo.role;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务资源和使用资源的树形结构实体
 * Created by JJH on 2017/12/20.
 * @author JJH
 */
@Data
public class ResourceTreeVO implements Serializable{
    private static final long serialVersionUID = 9221121657723108960L;
    /**
     * 资源对应的唯一序列 uuid
     */
    private String uuid;
    /**
     * 父资源的唯一序列
     */
    private String puuid;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 是否选中 （角色是否已经拥有此资源）
     */
    private Boolean checked;
}
