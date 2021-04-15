package server.aop.aspect;

import com.simplemvc.seaserver.annotation.aop.*;
import com.simplemvc.seaserver.core.aop.lang.JoinPoint;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Order(1)
public class TeacherAspect {
    @Pointcut("server.aop.*Service*")
    public void perAspect() {

    }


    @Before
    public void beforeAction(JoinPoint joinPoint) {
        log.info("aspect teacher before to do something. method:" + joinPoint.getTargetMethod());
    }

    @After
    public void afterAction(JoinPoint joinPoint) {
        log.info("aspect teacher after to do something,method:" + joinPoint.getTargetMethod());
    }
}
