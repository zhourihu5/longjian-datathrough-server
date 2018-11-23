package com.longfor.longjian.datathrough.app.config;

import com.longfor.gaia.gfs.data.redis.DynamicRedisProvider;
import com.longfor.gaia.gfs.data.redis.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * Created by Wang on 2018/11/23.
 */
@Configuration
public class RedisConfig {

    @Resource
    private DynamicRedisProvider dynamicRedisProvider;

    @Bean(name = "dataThroughRedis")
    public RedisTemplate dataThroughRedis() {
        StringRedisTemplate template = new StringRedisTemplate(dynamicRedisProvider.loadRedis().get("dataThroughRedis"));
        JacksonSerializer.setJacksonSerializer(template);
        return template;
    }



}
