package com.simplemvc.seaserver.core.aop.intercept;

import com.simplemvc.seaserver.annotation.aop.After;
import com.simplemvc.seaserver.annotation.aop.Before;
import com.simplemvc.seaserver.annotation.aop.Pointcut;
import com.simplemvc.seaserver.common.util.ReflectionUtil;
import com.simplemvc.seaserver.core.aop.lang.JoinPoint;
import com.simplemvc.seaserver.core.aop.lang.JoinPointIml;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class AspectInterceptor extends Interceptor {

    private Object adviceBean;
    private final List<Method> beforeMethods = new ArrayList<>();
    private final List<Method> afterMethods = new ArrayList<>();
    private final HashSet<String> expressionUrls = new HashSet<>();

    public AspectInterceptor(Object adviceBean) {
        this.adviceBean = adviceBean;
        init();
    }

    private void init() {
        Arrays.stream(adviceBean.getClass().getDeclaredMethods()).forEach(method -> {
            if (method.isAnnotationPresent(Pointcut.class)) {
                expressionUrls.add(method.getAnnotation(Pointcut.class).value());
            }
            if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            }
        });
    }


    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        JoinPoint joinPoint = new JoinPointIml(adviceBean, methodInvocation.getArgs(), methodInvocation.getTarget(), methodInvocation.getMethod());
        beforeMethods.forEach(method -> {
            ReflectionUtil.executeTargetMethod(adviceBean, method, joinPoint);
        });
        Object result = ReflectionUtil.executeTargetMethod(methodInvocation.getTarget(), methodInvocation.getMethod(), methodInvocation.getArgs());
        afterMethods.forEach(method -> {
            ReflectionUtil.executeTargetMethod(adviceBean, method, joinPoint);
        });
        return result;
    }
}
