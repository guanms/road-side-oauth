package com.tingbei.signup.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.tingbei.common.repository.UserInfoRepository;
import com.tingbei.common.repository.UserRoleInfoRepository;
import com.tingbei.signup.common.UpdateUserResourceCacheRunable;
import com.tingbei.signup.service.AuthorityService;
import com.tingbei.signup.service.UpdateUserResourceCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

@Service
public class UpdateUserResourceCacheServiceImpl implements UpdateUserResourceCacheService {

    @Autowired
    private UserRoleInfoRepository userRoleInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void updateUserResCacheByRiuuidOrUiuuid(String riUuid, String uiUuid) {
        // 更新缓存
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("updateCache-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(500), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        singleThreadPool.execute(new UpdateUserResourceCacheRunable(riUuid,userRoleInfoRepository,userInfoRepository,authorityService,uiUuid));
        singleThreadPool.shutdown();
    }
}
