package com.simplemvc.seaserver.common.util;

import com.simplemvc.seaserver.annotation.ioc.Component;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class ReflectionUtil {
    /**
     * 搜索路径下注解所对应的类
     *
     * @param packages
     * @param annotation
     * @return
     */
    public static Set<Class<?>> GetAnnotationClass(String[] packages, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(packages, new TypeAnnotationsScanner());
        Set<Class<?>> annotationClasses = reflections.getTypesAnnotatedWith(annotation);
        log.info("the number of class annotated with @" + annotation.getSimpleName() + "[{}]", annotationClasses.size());
        return annotationClasses;
    }

    /**
     * 获取bean的名字
     *
     * @param aclass
     * @return
     */
    public static String getBeanName(Class<?> aclass) {
        Component component = aclass.getAnnotation(Component.class);
        String className = aclass.getName();
        if (!Objects.isNull(component)) {
            className = !"".equals(component.name()) ? component.name() : className;
        }
        return className;
    }

    /**
     * 初始化对象
     * @param aClass
     * @return
     */
    public static Object newInstance(Class<?> aClass) {
        try {
          return aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw  new IllegalStateException(e.getMessage(),e);
        }
    }
}
