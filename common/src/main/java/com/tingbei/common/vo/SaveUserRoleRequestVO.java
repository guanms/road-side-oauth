package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 保存用户角色信息请求实体
 */
@Data
public class SaveUserRoleRequestVO implements Serializable{
    private static final long serialVersionUID = 2327184888828319018L;
    private String uiUuid;
    private List<String> ownerRoleList;
}
