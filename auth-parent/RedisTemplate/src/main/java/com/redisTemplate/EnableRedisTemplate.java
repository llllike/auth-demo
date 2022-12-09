package com.redisTemplate;


import com.redisTemplate.configuration.RedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisConfig.class})
@Documented
@Inherited
public @interface EnableRedisTemplate {
}
