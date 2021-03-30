package com.simplemvc.seaserver.core.aop.lang;

import java.lang.reflect.Method;

public interface JoinPoint {
    Object getAdviceBean();
    Object[] getArgs();
    Object getTarget();
    Method getTargetMethod();

}
