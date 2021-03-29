package com.simplemvc.seaserver.core.config;

import com.sun.org.apache.bcel.internal.generic.DRETURN;
import com.sun.tools.javac.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class YamlResourceLoader extends AbstractResourceLoader {
    @Override
    public Map<String, String> loadResources(Path path) throws IOException {
        Map<String, String> result = new LinkedHashMap<>();
        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(path))) {
            Object yaml = new Yaml().load(reader);
            buildFlattenedMap(result, (Map<String, Object>) yaml, null);
        }
        return result;
    }

    private void buildFlattenedMap(Map<String, String> result, Map<String, Object> source, String path) {
        source.forEach((key, value) -> {
            if (path != null && !path.isEmpty()) {
                key = path + "." + key;
            }
            if (value instanceof String) {
                result.put(key, String.valueOf(value));
            } else if (value instanceof Map) {
                buildFlattenedMap(result, (Map<String, Object>) value, key);
            } else {
                result.put(key, (value != null ? String.valueOf(value) : ""));
            }
        });
    }

    private Map<String, Object> asMap(Object object) {
        Map<String, Object> result = new LinkedHashMap<>();
        ((Map<Object, Object>) object).forEach((key, value) -> {
            if (value instanceof Map) {
                value = asMap(value);
            }
            result.put(key.toString(), value);
        });
        return result;
    }
}
