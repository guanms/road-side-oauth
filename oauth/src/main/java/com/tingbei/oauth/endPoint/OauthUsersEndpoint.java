package com.tingbei.oauth.endPoint;

import com.tingbei.common.vo.ServiceResultVO;
import com.tingbei.common.vo.UsersInfoParamVO;
import com.tingbei.oauth.service.OauthUsersService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 授权用户信息的端口
 */
@RestController
@RequestMapping("/vapor/oauthUser")
public class OauthUsersEndpoint {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private OauthUsersService oauthUsersService;

    /**
     * 注入
     */
    @Autowired
    public OauthUsersEndpoint(OauthUsersService oauthUsersService){
        this.oauthUsersService = oauthUsersService;
    }

    @ApiOperation(value = "插入授权中心用户信息",notes = "根据UsersInfoParamVO对象参数转成OauthUser入表，username已存在不插入")
    @PostMapping("/insert")
    public ServiceResultVO insertUserInfo(@ApiParam(name = "UsersInfoParamVO",value = "用户信息实体.\nusername:登陆名\npassword:密码(md5加密后)\nenabled:0不可用 1可用\nauthority:用户具有的权限(用于资源保护服务)",required = true)
            @RequestBody UsersInfoParamVO usersInfoParamVO){
        logger.info("接收到插入的用户信息为:"+usersInfoParamVO);
        ServiceResultVO resultVO = this.oauthUsersService.insertUserInfo(usersInfoParamVO);
        logger.info("返回给客户端的是："+resultVO);
        return resultVO;
    }

    @ApiOperation(value = "删除授权中心用户信息",notes = "根据get-url后面带的username去删除")
    @ApiImplicitParam(name = "userName",value = "用户ID",required = true,dataType = "String")
    @PostMapping("/delete")
    public ServiceResultVO deleteUserInfoByName(@RequestBody String userName){
        logger.info("接收到的删除的用户名为："+userName);
        ServiceResultVO resultVO = this.oauthUsersService.deleteUserInfoByName(userName);
        logger.info("返回给客户端的是："+resultVO);
        return resultVO;
    }

    @ApiOperation(value = "更新授权中心用户信息",notes = "根据UsersInfoParamVO对象参数中的username更新，不存在返回失败")
    @PostMapping("/update")
    public ServiceResultVO updateUserInfoByName(@ApiParam(name = "UsersInfoParamVO",value = "用户信息实体.\nusername:登陆名\npassword:密码(md5加密后)\nenabled:0不可用 1可用\nauthority:用户具有的权限(用于资源保护服务)",required = true)
            @RequestBody UsersInfoParamVO usersInfoParamVO){
        logger.info("接收到的更新的用户信息为："+ usersInfoParamVO);
        ServiceResultVO resultVO = this.oauthUsersService.updateUserInfoByName(usersInfoParamVO);
        logger.info("返回给客户端的是："+resultVO);
        return resultVO;
    }

    @PostMapping("/query")
    public ServiceResultVO queryUserInfo(@RequestBody UsersInfoParamVO usersInfoParamVO){
        return this.oauthUsersService.queryUsersInfo(usersInfoParamVO);
    }
}
