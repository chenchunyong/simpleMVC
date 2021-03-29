package com.simplemvc.seaserver.core.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ConfigurationManager extends AbstractConfiguration {
    private static final String PROPERTIES_FILE_EXTENSION = ".properties";
    private static final String[] YAML_FILE_EXTENSION = {".yaml", "yml"};
    private Configuration configuration;

    public ConfigurationManager(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int getInt(String id) {
        return configuration.getInt(id);
    }

    @Override
    public String getString(String id) {
        return configuration.getString(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        return configuration.getBoolean(id);
    }

    @Override
    public void loadResources(List<Path> resourcePaths) {
        resourcePaths.forEach(path -> {
            String fileName = path.getFileName().toString();
            try {
                if (fileName.endsWith(PROPERTIES_FILE_EXTENSION)) {
                    ResourceLoader resourceLoader = new PropertiesResourceLoader();
                    putAll(resourceLoader.loadResource(path));
                } else if (Arrays.stream(YAML_FILE_EXTENSION).anyMatch(p -> fileName.endsWith(p))) {
                    ResourceLoader resourceLoader = new YamlResourceLoader();
                    putAll(resourceLoader.loadResource(path));
                }
            } catch (IOException e) {
                log.error(e.getMessage());
            }

        });
    }
}
