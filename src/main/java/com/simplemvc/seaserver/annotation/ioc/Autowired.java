package com.simplemvc.seaserver.annotation.ioc;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
}
