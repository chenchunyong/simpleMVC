package com.simplemvc.seaserver.core.config;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public abstract class AbstractResourceLoader implements ResourceLoader {
    @Override
    public Map<String, String> loadResource(Path path) throws IOException {
        return loadResources(path);
    }

    public abstract Map<String, String> loadResources(Path path) throws IOException;
}
