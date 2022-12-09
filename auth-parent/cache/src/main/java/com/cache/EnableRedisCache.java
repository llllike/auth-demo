package com.cache;

import com.cache.config.RedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisConfig.class})
@Documented
@Inherited
@EnableCaching
public @interface EnableRedisCache {

}
