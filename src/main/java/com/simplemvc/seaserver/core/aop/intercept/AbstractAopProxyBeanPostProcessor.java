package com.simplemvc.seaserver.core.aop.intercept;

import com.simplemvc.seaserver.core.aop.factory.InterceptorFactory;

public abstract class AbstractAopProxyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean) {
        Object wrapperProxyBean = bean;
        for (Interceptor interceptor : InterceptorFactory.getInterceptors()) {
            if (interceptor.support(bean)) {
                wrapperProxyBean = wrapBean(bean, interceptor);
            }
        }
        return wrapperProxyBean;

    }

    public abstract Object wrapBean(Object target, Interceptor interceptor);
}
