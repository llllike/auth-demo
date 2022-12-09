package com.trans;


import com.trans.config.emailConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({emailConfiguration.class})
@Documented
@Inherited
public @interface EnableEmail {
}
