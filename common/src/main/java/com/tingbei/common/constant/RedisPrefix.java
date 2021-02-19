package com.tingbei.common.constant;

public enum RedisPrefix {
    /**
     * 手机端微信公众号粉丝权限redis-key前缀
     * 后面跟上 openId
     */
    MOBILE_WECHAT_FANS_AUTHORITY("ac:mobile:wechat:fans:authority:"),

    /**
     * 授权中心登录用户信息
     * 后面跟上 token
     */
    PAGE_LOGIN_USERINFO("ac:page:login:userinfo:"),

    /**
     * 授权中心开放给外部各个系统授权鉴权的信息缓存key前缀
     * 后面跟上 token
     */
    PUBLIC_LOGIN_TOKENANDAUTHORITY("ac:public:login:tokenAuthority:");

    RedisPrefix(String prefix){
        this.prefix = prefix;
    }

    private String prefix;

    public String getPrefix() {
        return prefix;
    }
}
