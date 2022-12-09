package com.antiSamy;


import com.antiSamy.config.XssAuthConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({XssAuthConfiguration.class})
@Documented
@Inherited
public @interface EnableAntiSamy {
}
