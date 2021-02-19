package com.tingbei.common.vo.userresource;


import com.tingbei.common.vo.UserInfoVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户和所有服务资源和使用范围资源列表
 * 资源列表为父子结构
 * Created by JJH on 2018/1/9.
 * @author JJH
 */
@Data
public class UserAndResourceVO implements Serializable{
    private static final long serialVersionUID = 7317031531793903964L;
    /**
     * 用户信息
     */
    private UserInfoVO userInfo;
    /**
     * 使用范围资源列表(展示资源)
     */
    private List<ExtendUseScopeVo> showScopeList;
    /**
     * 使用范围资源列表（可操作）
     */
    private List<ExtendUseScopeVo> selectScopeList;

    /**
     * 服务资源（展示资源）
     */
    private List<ExtendServiceResourceVo> showSrList;
    /**
     * 服务资源（可操作资源）
     */
    private List<ExtendServiceResourceVo> selectSrList;

}
