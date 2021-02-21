package com.tingbei.common.client;

import com.tingbei.common.vo.ServiceResultVO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户基本信息服务访问客户端
 */
@Component
@FeignClient("signup")
public interface UserBaseChainInfoClient {
    @RequestMapping(value = "/baseInfo/query/baseChainInfo",method = RequestMethod.POST)
    ServiceResultVO queryUserBaseChainInfo(@RequestBody String userName);

    @RequestMapping(value = "/baseInfo/operation/delete/userRefrence",method = RequestMethod.POST)
    int cascadeDeleteRefrence(@RequestBody String userName);
}
