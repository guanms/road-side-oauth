package com.tingbei.zuul.filter;/**
 * @Author:JXW
 * @Date:2021/2/20 23:39
 */


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@Author:JXW
 *@Date:2021/2/20 23:39
 */
@Component
public class WebFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String currentOrigin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", currentOrigin);   //  允许所有域名的请求
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.setHeader("Access-Control-Allow-Headers",
                "User-Agent,Origin,Cache-Control,Content-type,Date,Server,withCredentials,AccessToken");
        response.setHeader("Access-Control-Expose-Headers", "CUSTOMSESSIONID");
        response.setHeader("Access-Control-Request-Headers", "CUSTOMSESSIONID");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-auth-token,Origin,Access-Token,X-Requested-With,Content-Type,Accept");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
