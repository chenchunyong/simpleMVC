package com.simplemvc.seaserver.core.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesResourceLoader extends AbstractResourceLoader {
    @Override
    public Map<String, String> loadResources(Path path) throws IOException {
        Properties properties = new Properties();

        try (InputStream stream = Files.newInputStream(path); Reader reader = new InputStreamReader(stream)) {
            properties.load(reader);
        }
        Map<String, String> map = new HashMap<>(properties.size());
        properties.entrySet().forEach(property -> {
            map.put(property.getKey().toString(), property.getValue().toString());
        });
        return map;

    }
}
