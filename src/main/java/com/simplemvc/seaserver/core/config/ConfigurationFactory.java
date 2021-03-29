package com.simplemvc.seaserver.core.config;

public class ConfigurationFactory {

    public static Configuration getConfig() {
        return SingleConfigurationHolder.CONFIGURATION;
    }

    public static class SingleConfigurationHolder {
        public static final Configuration CONFIGURATION = new DefaultConfiguration();
    }
}
