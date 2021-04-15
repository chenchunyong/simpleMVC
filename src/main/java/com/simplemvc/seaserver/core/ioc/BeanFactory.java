package com.simplemvc.seaserver.core.ioc;

import com.simplemvc.seaserver.annotation.ioc.Component;
import com.simplemvc.seaserver.common.util.ReflectionUtil;
import com.simplemvc.seaserver.core.aop.factory.InterceptorFactory;
import com.simplemvc.seaserver.core.aop.intercept.AopProxyBeanPostProcessorFactory;
import com.simplemvc.seaserver.core.aop.intercept.BeanPostProcessor;
import com.simplemvc.seaserver.core.common.ClassFactory;
import com.simplemvc.seaserver.core.config.ConfigurationFactory;
import com.simplemvc.seaserver.core.config.ConfigurationManager;
import com.simplemvc.seaserver.exception.DoGetBeanException;


import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BeanFactory {

    public static final Map<String, Object> BEANS = new ConcurrentHashMap<>();
    private static final Map<String, String[]> SINGLE_BEAN_NAMES_TYPE_MAP = new ConcurrentHashMap<>(128);
    private static final List<Class<? extends Annotation>> LOADED_ANNOTATION = new ArrayList<>();

    static {
        LOADED_ANNOTATION.add(Component.class);
    }


    /**
     * 初始化实例
     */
    public static void initBean(String[] packageName) {
        // 实例化对象
        LOADED_ANNOTATION.forEach(annotation -> {
            for (Class<?> aClass : ClassFactory.CLASS.get(annotation)) {
                Object beanInstance = ReflectionUtil.newInstance(aClass);
                BEANS.put(ReflectionUtil.getBeanName(aClass), beanInstance);
            }
        });
        BEANS.put(ConfigurationManager.class.getName(), new ConfigurationManager(ConfigurationFactory.getConfig()));
        applyBeanPostProcessor(packageName);
    }

    public static void injectProperties(String[] packageNames) {
        // 初始化依赖注入
        AutowiredBeanInitialization beanInitialization = new AutowiredBeanInitialization(packageNames);
        BEANS.values().forEach(beanInitialization::initialize);
    }

    public static void applyBeanPostProcessor(String[] packageNames) {
        InterceptorFactory.loadInterceptor(packageNames);
        BEANS.replaceAll((beanName, beanInstance) -> {
            BeanPostProcessor beanPostProcessor = AopProxyBeanPostProcessorFactory.get(beanInstance.getClass());
            return beanPostProcessor.postProcessAfterInitialization(beanInstance);
        });
    }

    public static <T> T getBean(Class<T> type) {
        String[] beanNames = getBeanNamesForType(type);
        if (beanNames.length == 0) {
            throw new DoGetBeanException("not fount bean implement，the bean :" + type.getName());
        }
        Object beanInstance = BEANS.get(beanNames[0]);
        if (!type.isInstance(beanInstance)) {
            throw new DoGetBeanException("not fount bean implement，the bean :" + type.getName());
        }
        return type.cast(beanInstance);
    }

    public static String[] getBeanNamesForType(Class<?> type) {
        String beanName = type.getName();
        String[] beanNames = SINGLE_BEAN_NAMES_TYPE_MAP.get(beanName);
        if (Objects.isNull(beanNames)) {
            List<String> beanNamesList = new ArrayList<>();
            BEANS.entrySet().forEach(bean -> {
                Class<?> beanClass = bean.getValue().getClass();
                if (type.isInterface()) {
                    Class<?>[] beanClassInterfaces = beanClass.getInterfaces();
                    Arrays.stream(beanClassInterfaces).forEach(c -> {
                        if (c.getName().equals(beanName)) {
                            beanNamesList.add(bean.getKey());
                        }
                    });
                } else if (beanClass.isAssignableFrom(type)) {
                    beanNamesList.add(bean.getKey());
                }
            });
            beanNames = beanNamesList.toArray(new String[0]);
            SINGLE_BEAN_NAMES_TYPE_MAP.put(beanName, beanNames);
        }
        return beanNames;
    }
}
