package com.simplemvc.seaserver.core;

import com.simplemvc.seaserver.core.aop.factory.InterceptorFactory;
import com.simplemvc.seaserver.core.boot.ComponentScanHandler;
import com.simplemvc.seaserver.core.config.Configuration;
import com.simplemvc.seaserver.core.config.ConfigurationManager;
import com.simplemvc.seaserver.core.ioc.BeanFactory;
import com.simplemvc.seaserver.core.common.ClassFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplicationContext {
    private final static ApplicationContext applicationContext = new ApplicationContext();

    public void run(Class<?> applicationClass) {
        String[] packageNames = ComponentScanHandler.getComponentPackage(applicationClass);
        ClassFactory.loadClass(packageNames);
        BeanFactory.initBean();
        loadResources(applicationClass);
        BeanFactory.injectProperties(packageNames);
        InterceptorFactory.loadInterceptor(packageNames);
    }

    private void loadResources(Class<?> applicationClass) {
        ClassLoader classLoader = applicationClass.getClassLoader();
        List<Path> filePaths = new ArrayList<>();
        for (String configName : Configuration.DEFAULT_CONFIG_NAMES) {
            URL url = classLoader.getResource(configName);
            if (!Objects.isNull(url)) {
                try {
                    filePaths.add(Paths.get(url.toURI()));
                } catch (URISyntaxException ignored) {
                }
            }
        }
        ConfigurationManager configurationManager = BeanFactory.getBean(ConfigurationManager.class);
        configurationManager.loadResources(filePaths);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
