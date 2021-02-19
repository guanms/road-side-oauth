package com.tingbei.common.vo.role;



import com.tingbei.common.vo.param.PageParam;

import java.io.Serializable;

/**
 * 角色信息分页查询参数实体
 * Created by JJH on 2017/12/19.
 * @author JJH
 */
public class RolePageQueryParam extends PageParam implements Serializable{
    private static final long serialVersionUID = 7719821790707426563L;
    private String riRoleDesc;

    public String getRiRoleDesc() {
        return riRoleDesc;
    }

    public void setRiRoleDesc(String riRoleDesc) {
        this.riRoleDesc = riRoleDesc;
    }
}
