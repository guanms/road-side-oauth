package com.tingbei.common.vo;


import com.tingbei.common.vo.userresource.UserAndResourceVO;
import lombok.Data;

import java.io.Serializable;

/**
 * token授权信息和用户资源列表
 * Created by JJH on 2018/1/11.
 * @author JJH
 */
@Data
public class TokenAndResourceVo implements Serializable{
    private static final long serialVersionUID = -8186619724752334282L;
    private AuthTokenInfo authTokenInfo;
    private UserAndResourceVO userAndResourceVO;
}
