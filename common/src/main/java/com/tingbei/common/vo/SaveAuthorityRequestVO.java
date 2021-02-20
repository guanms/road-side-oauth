package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 保存角色 权限请求实体
 * Created by JJH on 2017/12/21.
 * @author JJH
 */
@Data
public class SaveAuthorityRequestVO implements Serializable{
    private static final long serialVersionUID = 3489258104065736014L;
    private String riUuid;
    private List<String> useScopeList;
    private List<String> serviceList;
    private String resourceType;
    /**
     * 操作级别 （1.show 展示资源  2.select 选择资源）
     */
    private String operateLevel;
}
