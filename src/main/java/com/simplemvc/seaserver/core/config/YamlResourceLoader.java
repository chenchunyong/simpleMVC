package com.simplemvc.seaserver.core.config;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class YamlResourceLoader extends AbstractResourceLoader {
    @Override
    public Map<String, String> loadResources(Path path) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(Files.newInputStream(path))) {
            Yaml yaml = new Yaml().load(reader);

        }
        return null;
    }
}
