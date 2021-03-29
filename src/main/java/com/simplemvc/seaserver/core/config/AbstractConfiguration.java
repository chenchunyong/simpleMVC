package com.simplemvc.seaserver.core.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractConfiguration implements Configuration {

    private static final Map<String, String> CONFIGURATION_CACHE = new ConcurrentHashMap<>();

    @Override
    public int getInt(String id) {
        String result = CONFIGURATION_CACHE.get(id);
        return Integer.parseInt(result);
    }

    @Override
    public String getString(String id) {
        return CONFIGURATION_CACHE.get(id);
    }

    @Override
    public Boolean getBoolean(String id) {
        String result = CONFIGURATION_CACHE.get(id);
        return Boolean.parseBoolean(result);
    }

    @Override
    public void put(String id, String value) {
        CONFIGURATION_CACHE.put(id, value);
    }

    @Override
    public void putAll(Map<String, String> maps) {
        CONFIGURATION_CACHE.putAll(maps);
    }
}
