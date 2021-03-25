package com.simplemvc.seaserver.core.ioc;

import com.simplemvc.seaserver.annotation.ioc.Autowired;
import com.simplemvc.seaserver.annotation.ioc.Qualifier;
import com.simplemvc.seaserver.common.util.ReflectionUtil;
import com.simplemvc.seaserver.exception.CanNotDetermineTargetBeanException;
import com.simplemvc.seaserver.exception.InterfaceNotHaveImplementedClassException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public class AutowiredBeanInitialization {
    private String[] packageNames;

    public AutowiredBeanInitialization(String[] packageNames) {
        this.packageNames = packageNames;
    }

    /**
     * 初始化依赖注入
     */
    public void initialize() {
        BeanFactory.BEANS.values().forEach(beanInstance -> {
            Field[] fields = beanInstance.getClass().getDeclaredFields();
            Arrays.stream(fields).forEach(field -> {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object beanFieldInstance = getAutowiredFieldInstance(field, packageNames);
                }
            });
        });

    }

    private Object getAutowiredFieldInstance(Field beanField, String[] packageNames) {
        Class<? extends Field> beanFieldClass = beanField.getClass();
        String beanName = ReflectionUtil.getBeanName(beanFieldClass);
        if (beanFieldClass.isInterface()) {
            Set<Class<? extends Field>> subTypeClass = (Set<Class<? extends Field>>) ReflectionUtil.getSubTypeClass(packageNames, beanField.getClass());
            if (subTypeClass.size() == 0) {
                throw new InterfaceNotHaveImplementedClassException(beanField.getName() + " is interface but do not have implemented class exception");
            } else if (subTypeClass.size() == 1) {
                beanName = ReflectionUtil.getBeanName(subTypeClass.stream().findFirst().get());
            } else {
                Qualifier autowiredName = beanField.getAnnotation(Qualifier.class);
                beanName = Objects.isNull(autowiredName) ? beanName : autowiredName.value();
            }
        }
        Object beanFieldInstance = BeanFactory.BEANS.get(beanName);
        if (Objects.isNull(beanFieldInstance)) {
            throw new CanNotDetermineTargetBeanException("can not determine target bean of" + beanFieldClass.getName());
        }
        return beanFieldInstance;
    }
}
