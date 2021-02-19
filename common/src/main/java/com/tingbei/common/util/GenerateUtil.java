package com.tingbei.common.util;

import com.tingbei.common.vo.ServiceResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GenerateUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CommonUtil commonUtil;

    /**
     * 注入
     */
    @Autowired
    public GenerateUtil(CommonUtil commonUtil) {
        this.commonUtil = commonUtil;
    }

    /**
     * 生成服务返回值
     */
    public<T> ServiceResultVO<T> generageServiceResultVO(boolean success, String code, String msg, T data){
        ServiceResultVO<T> serviceResultVO = new ServiceResultVO<>();
        serviceResultVO.setSuccess(success);
        serviceResultVO.setCode(code);
        serviceResultVO.setMessage(msg);
        serviceResultVO.setData(data);
        return serviceResultVO;
    }

}
