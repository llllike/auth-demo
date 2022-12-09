package com.JWT;

import com.JWT.configuration.AuthJWTConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({AuthJWTConfiguration.class})
@Documented
@Inherited
public @interface EnableJWT {
}
