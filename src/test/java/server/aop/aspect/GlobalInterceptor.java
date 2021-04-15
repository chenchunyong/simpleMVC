package server.aop.aspect;

import com.simplemvc.seaserver.core.aop.intercept.Interceptor;
import com.simplemvc.seaserver.core.aop.intercept.MethodInvocation;
import server.aop.StudentService;

public class GlobalInterceptor extends Interceptor {
    @Override
    public boolean support(Object bean) {
        return bean instanceof StudentService;
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        System.out.println(GlobalInterceptor.class.getSimpleName() + " before method：" + methodInvocation.getMethod().getName());
        Object result = methodInvocation.proceed();
        System.out.println(GlobalInterceptor.class.getSimpleName() + " after method：" + methodInvocation.getMethod().getName());
        return result;
    }
}
