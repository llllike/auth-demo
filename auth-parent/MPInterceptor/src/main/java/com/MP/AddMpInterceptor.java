package com.MP;


import com.MP.configuration.MPConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MPConfiguration.class})
@Documented
@Inherited
public @interface AddMpInterceptor {
}
