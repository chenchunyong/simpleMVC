package com.simplemvc.seaserver.core.common;

import com.simplemvc.seaserver.annotation.ioc.Component;
import com.simplemvc.seaserver.common.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

    public static final Map<String, Object> BEANS = new ConcurrentHashMap<>();
    private static final List<Class<? extends Annotation>> LOADED_ANNOTATION = new ArrayList<>();

    static {
        LOADED_ANNOTATION.add(Component.class);
    }


    /**
     * 初始化实例
     */
    public static void initBean() {
        LOADED_ANNOTATION.forEach(annotation -> {
            for (Class<?> aClass : ClassFactory.CLASS.get(annotation)) {
                BEANS.put(ReflectionUtil.getBeanName(aClass), ReflectionUtil.newInstance(aClass));
            }
        });
    }
}
