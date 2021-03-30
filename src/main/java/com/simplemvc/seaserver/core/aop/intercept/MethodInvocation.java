package com.simplemvc.seaserver.core.aop.intercept;

import com.simplemvc.seaserver.common.util.ReflectionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@AllArgsConstructor
@Getter
public class MethodInvocation {

    private final Object target;
    private final Method method;
    private final Object[] args;

    public Object proceed() {
        return ReflectionUtil.executeTargetMethod(target, method, args);
    }


}
