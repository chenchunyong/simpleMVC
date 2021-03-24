package com.simplemvc.seaserver.annotation.ioc;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Qualifier {
    String value() default "";
}
