package com.simplemvc.seaserver.core.aop.intercept;

import com.simplemvc.seaserver.core.aop.proxy.CglibAspectProxy;

public class CglibAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {
    @Override
    public Object wrapBean(Object target, Interceptor interceptor) {
        return CglibAspectProxy.wrap(target, interceptor);
    }
}
