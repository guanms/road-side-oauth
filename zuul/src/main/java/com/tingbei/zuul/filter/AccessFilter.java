package com.tingbei.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String filterType() {
        // pre：路由之前
        // routing：路由之时
        // post： 路由之后
        // error：发送错误调用
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0; //过滤的顺序,数字越小，优先级越高
    }

    @Override
    public boolean shouldFilter() {
        return true; // 是否要过滤
    }

    @Override
    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("Request-Info --- IP:{}  HttpMethod:{}  url:{}", request.getRemoteAddr(), request.getMethod(), request.getRequestURL().toString());
        return null;
    }
}
