package com.zym.submit.config;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

/**
 * //默认如果保存对象jdk序列化机制，系列化后的数据保存到redis。默认的机制会将保存的对象序列化。
 *         //redisTemplate.opsForValue().set("allActivityTypes",arrayList);
 *
 *         //修改默认的序列化机制，将系列化的内容改成json格式。将内容json化。
 * 版权声明：本文为CSDN博主「小张帅三代」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_38313548/article/details/100998133
 * @author zym
 * @date 2020-05-17-14:28
 */
@Configuration
public class MyRedisConfig {
    @Bean
    public RedisTemplate<Object, Object> JsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return template;
        //RedisAutoConfiguration;
    }
}
