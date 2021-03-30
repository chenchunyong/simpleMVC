package com.simplemvc.seaserver.core.aop.lang;

import java.lang.reflect.Method;

public class JoinPointIml implements JoinPoint {
    private Object adviceBean;
    private Object[] args;
    private Object target;
    private Method targetMethod;

    public JoinPointIml(Object adviceBean, Object[] args, Object target, Method targetMethod) {

        this.adviceBean = adviceBean;
        this.args = args;
        this.target = target;
        this.targetMethod = targetMethod;
    }

    @Override
    public Object getAdviceBean() {
        return adviceBean;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Method getTargetMethod() {
        return null;
    }
}
