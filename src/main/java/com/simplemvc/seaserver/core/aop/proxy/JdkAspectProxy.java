package com.simplemvc.seaserver.core.aop.proxy;

import com.simplemvc.seaserver.core.aop.intercept.Interceptor;
import com.simplemvc.seaserver.core.aop.intercept.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkAspectProxy implements InvocationHandler {
    private Object target;
    private Interceptor interceptor;

    public JdkAspectProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        JdkAspectProxy proxy = new JdkAspectProxy(target, interceptor);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), proxy);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        return interceptor.intercept(methodInvocation);
    }
}
