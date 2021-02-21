package com.tingbei.signup.service;

public interface UpdateUserResourceCacheService {

    /**
     * 更新用户资源缓存
     * @param riUuid 角色uuid
     * @param uiUuid 用户uuid
     */
    void updateUserResCacheByRiuuidOrUiuuid(String riUuid, String uiUuid);
}
