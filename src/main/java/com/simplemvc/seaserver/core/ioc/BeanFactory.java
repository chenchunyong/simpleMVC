package com.simplemvc.seaserver.core.ioc;

import com.simplemvc.seaserver.annotation.ioc.Autowired;
import com.simplemvc.seaserver.annotation.ioc.Component;
import com.simplemvc.seaserver.annotation.ioc.Qualifier;
import com.simplemvc.seaserver.common.util.ReflectionUtil;
import com.simplemvc.seaserver.core.common.ClassFactory;
import com.simplemvc.seaserver.exception.CanNotDetermineTargetBeanException;
import com.simplemvc.seaserver.exception.InterfaceNotHaveImplementedClassException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
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
    public static void initBean(String[] packageNames) {
        // 实例化对象
        LOADED_ANNOTATION.forEach(annotation -> {
            for (Class<?> aClass : ClassFactory.CLASS.get(annotation)) {
                Object beanInstance = ReflectionUtil.newInstance(aClass);
                BEANS.put(ReflectionUtil.getBeanName(aClass), beanInstance);
            }
        });
        // 初始化依赖注入
        AutowiredBeanInitialization beanInitialization = new AutowiredBeanInitialization(packageNames);
        beanInitialization.initialize();
    }
}
