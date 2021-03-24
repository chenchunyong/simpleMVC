package com.simplemvc.seaserver.core.boot;


import com.simplemvc.seaserver.annotation.boot.ComponentScan;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class ComponentScanHandler {

    public static String[] getComponentPackage(Class<?> applicationContext) {

        ComponentScan componentScan = applicationContext.getAnnotation(ComponentScan.class);
        String[] packageNames = !Objects.isNull(componentScan) ? componentScan.value() :
                new String[]{applicationContext.getPackage().getName()};
        log.info("loaded packages:{}", Arrays.asList(packageNames));
        return packageNames;
    }
}
