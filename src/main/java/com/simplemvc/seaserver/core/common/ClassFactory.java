package com.simplemvc.seaserver.core.common;

import com.simplemvc.seaserver.annotation.aop.Aspect;
import com.simplemvc.seaserver.annotation.ioc.Component;
import com.simplemvc.seaserver.common.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ClassFactory {
    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASS = new ConcurrentHashMap<>();
    private static final List<Class<? extends Annotation>> LOADED_ANNOTATION = new ArrayList<>();

    static {
        LOADED_ANNOTATION.add(Component.class);
        LOADED_ANNOTATION.add(Aspect.class);
    }

    /**
     * 加载所需要的类型
     *
     * @param packageNames
     */
    public static void loadClass(String[] packageNames) {
        LOADED_ANNOTATION.forEach(annotation -> {
            Set<Class<?>> classSet = ReflectionUtil.GetAnnotationClass(packageNames, annotation);
            CLASS.put(annotation, classSet);
            log.info("the number of class annotated with @" + annotation.getSimpleName() + "[{}]", classSet.size());
        });
    }
}
