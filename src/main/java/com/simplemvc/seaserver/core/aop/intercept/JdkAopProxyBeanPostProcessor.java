package com.simplemvc.seaserver.core.aop.intercept;

import com.simplemvc.seaserver.core.aop.proxy.JdkAspectProxy;

public class JdkAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {
    @Override
    public Object wrapBean(Object target, Interceptor interceptor) {
        return JdkAspectProxy.wrap(target, interceptor);
    }
}
