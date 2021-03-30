package com.simplemvc.seaserver.core.aop.intercept;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Interceptor {
    private int order = -1;

    public boolean support(Object bean) {
        return false;
    }

    public abstract Object intercept(MethodInvocation methodInvocation);
}
