package com.mv;


import com.mv.config.MVConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MVConfiguration.class})
@Documented
@Inherited
public @interface EnableMatrixVariable {

}
