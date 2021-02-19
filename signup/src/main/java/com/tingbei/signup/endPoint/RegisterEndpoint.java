package com.tingbei.signup.endPoint;

import com.tingbei.common.util.GenerateUtil;
import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UserInfoVO;
import com.tingbei.signup.service.RegisterService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class RegisterEndpoint {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private GenerateUtil generateUtil;

    private RegisterService registerService;


    @CrossOrigin
    @GetMapping("/routePermission")
    public ServiceResultVO registerPermissionFromCarLane(String identityCode, String username, String createMan, String version){

        if (StringUtils.isBlank(identityCode) || StringUtils.isBlank(username)|| StringUtils.isBlank(version) || StringUtils.isBlank(createMan)) {
            //表单数据有误则还返回到登录页
            return this.generateUtil.generageServiceResultVO(false,null,"注册信息不能为空",null);

        }else {

            try{

                UserInfoVO userInfo = this.registerService.queryOneUserInfoByUserName(identityCode);

                if(null != userInfo){
                    //用户名密码已经存在
                    return this.generateUtil.generageServiceResultVO(false,null,"该用户已经存在",null);

                }else{

                    return registerService.createUserInfo( identityCode, username, createMan, version);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return this.generateUtil.generageServiceResultVO(false,null,"注册失败",null);
    }


}
