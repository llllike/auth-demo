package com.logba.annotation;

import com.logba.config.LogAutoConfiguration;
import com.logba.init.ApplicationLoggerInitializer;
import com.logba.service.LogService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;



@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({LogAutoConfiguration.class, ApplicationLoggerInitializer.class, LogService.class})
@Documented
@Inherited
public @interface EnableLogback {

}
