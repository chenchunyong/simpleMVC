package com.simplemvc.seaserver.annotation.boot;

import com.simplemvc.seaserver.annotation.ioc.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface StartApplication {
}
