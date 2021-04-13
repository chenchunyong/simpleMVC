package com.simplemvc.seaserver.core.aop.proxy;

import com.simplemvc.seaserver.core.aop.intercept.Interceptor;
import com.simplemvc.seaserver.core.aop.intercept.MethodInvocation;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibAspectProxy implements MethodInterceptor {
    private Object target;
    private Interceptor interceptor;

    public CglibAspectProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor) {
        Class<?> rootClass = target.getClass();
        Class<?> proxySuperClass = rootClass;
        // cglib 多级代理处理
        if (target.getClass().getName().contains("$$")) {
            proxySuperClass = rootClass.getSuperclass();
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(target.getClass().getClassLoader());
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setCallback(new CglibAspectProxy(target, interceptor));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, objects);
        return interceptor.intercept(methodInvocation);

    }
}
