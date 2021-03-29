package com.simplemvc.seaserver.core.ioc;

import com.simplemvc.seaserver.annotation.ioc.Autowired;
import com.simplemvc.seaserver.annotation.ioc.Qualifier;
import com.simplemvc.seaserver.annotation.value.Value;
import com.simplemvc.seaserver.common.util.ObjectUtil;
import com.simplemvc.seaserver.common.util.ReflectionUtil;
import com.simplemvc.seaserver.core.config.ConfigurationManager;
import com.simplemvc.seaserver.exception.CanNotDetermineTargetBeanException;
import com.simplemvc.seaserver.exception.InterfaceNotHaveImplementedClassException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AutowiredBeanInitialization {
    private String[] packageNames;
    private static Map<String, Object> SINGLETON_OBJECT = new ConcurrentHashMap<>();

    public AutowiredBeanInitialization(String[] packageNames) {
        this.packageNames = packageNames;
    }

    /**
     * 初始化依赖注入
     */
    public void initialize(Object beanInstance) {
        Field[] fields = beanInstance.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            if (field.isAnnotationPresent(Autowired.class)) {
                String beanFieldName = ReflectionUtil.getBeanName(field.getType());
                Object beanFieldInstance = getAutowiredFieldInstance(field);
                beanFieldInstance = resolveCircularDependency(beanFieldInstance, beanFieldName);
                ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
            }
            if (field.isAnnotationPresent(Value.class)) {
                Object convertValue = getValueFieldValue(field);
                ReflectionUtil.setField(beanInstance, field, convertValue);
            }
        });
    }

    public Object resolveCircularDependency(Object beanFieldInstance, String beanFieldName) {
        if (SINGLETON_OBJECT.containsKey(beanFieldName)) {
            return SINGLETON_OBJECT.get(beanFieldName);
        }
        SINGLETON_OBJECT.put(beanFieldName, beanFieldInstance);
        initialize(beanFieldInstance);
        return beanFieldInstance;
    }

    /**
     * 获取Field属性的实例
     *
     * @param beanField
     * @return
     */
    private Object getAutowiredFieldInstance(Field beanField) {
        Class<?> beanFieldClass = beanField.getType();
        String beanName = ReflectionUtil.getBeanName(beanFieldClass);
        if (beanFieldClass.isInterface()) {
            Set<Class<?>> subTypeClass = ReflectionUtil.getSubTypeClass(packageNames, (Class<Object>) beanFieldClass);
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

    private Object getValueFieldValue(Field beanField) {
        String key = beanField.getDeclaredAnnotation(Value.class).value();
        ConfigurationManager configurationManager = BeanFactory.getBean(ConfigurationManager.class);
        String value = configurationManager.getString(key);
        if (value == null) {
            throw new IllegalArgumentException("can not find target value for property:{" + key + "}");
        }
        return ObjectUtil.convert(beanField.getType(), value);
    }
}
