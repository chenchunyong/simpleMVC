package com.simplemvc.seaserver.core.aop.factory;

import com.simplemvc.seaserver.annotation.aop.Aspect;
import com.simplemvc.seaserver.annotation.aop.Order;
import com.simplemvc.seaserver.common.util.ReflectionUtil;
import com.simplemvc.seaserver.core.aop.intercept.AspectInterceptor;
import com.simplemvc.seaserver.core.aop.intercept.Interceptor;
import com.simplemvc.seaserver.core.common.ClassFactory;
import com.simplemvc.seaserver.exception.CannotInitializeConstructorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InterceptorFactory {
    private static List<Interceptor> interceptors = new ArrayList<>();

    public static void loadInterceptor(String[] packageNames) {
        // 获取实现interceptor的类
        Set<Class<? extends Interceptor>> interceptorClass = ReflectionUtil.getSubTypeClass(packageNames, Interceptor.class);
        interceptorClass.forEach(interceptor -> {
            try {
                interceptors.add(interceptor.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                throw new CannotInitializeConstructorException(e.getMessage());
            }
        });
        // 获取被 @Aspect 标记的类
        Set<Class<?>> aspectClass = ClassFactory.CLASS.get(Aspect.class);
        if (!aspectClass.isEmpty()) {
            aspectClass.forEach(aClass -> {
                Object aspectInstance = ReflectionUtil.newInstance(aClass);
                AspectInterceptor interceptor = new AspectInterceptor(aspectInstance);
                if (aClass.isAnnotationPresent(Order.class)) {
                    interceptor.setOrder(aClass.getAnnotation(Order.class).value());
                }
                interceptors.add(interceptor);
            });
        }
        interceptors = interceptors.stream().sorted(Comparator.comparing(Interceptor::getOrder)).collect(Collectors.toList());

    }
}
