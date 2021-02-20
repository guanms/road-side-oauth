package com.tingbei.oauth.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * redisTemplate配置类
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jsonRedisSerializer.setObjectMapper(om);
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setValueSerializer(jsonRedisSerializer);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @Autowired
    public CacheManager cacheManager(RedisTemplate<String,Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // Number of seconds before expiration. Defaults to unlimited (0)
        //设置key-value超时时间
        cacheManager.setDefaultExpiration(600);
        return cacheManager;
    }

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        String[] classNameMethod = {"com.tingbei.oauth.service.impl.AuthorityServiceImpl.queryUserAllResource","com.tingbei.oauth.service.impl.AuthorityServiceImpl.updateCacheQueryUserAllResource"};
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("ac:methodCache:");
            String pre = target.getClass().getName() + "." + method.getName();
            if(classNameMethod[0].equals(pre) ||classNameMethod[1].equals(pre)){
                sb.append("authorityService:userAllResource:");
            }else {
                sb.append(target.getClass().getName());
                sb.append(":");
                sb.append(method.getName());
            }
            for (Object obj : params) {
                sb.append(obj.toString() + "&");
            }
            return sb.toString();
        };
    }
}
