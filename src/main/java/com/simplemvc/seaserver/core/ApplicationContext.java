package com.simplemvc.seaserver.core;

import com.simplemvc.seaserver.core.boot.ComponentScanHandler;
import com.simplemvc.seaserver.core.common.BeanFactory;
import com.simplemvc.seaserver.core.common.ClassFactory;

public class ApplicationContext {
    private final static ApplicationContext applicationContext = new ApplicationContext();

    public void run(Class<?> applicationClass) {
        String[] packageNames = ComponentScanHandler.getComponentPackage(applicationClass);
        ClassFactory.loadClass(packageNames);
        BeanFactory.initBean();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
