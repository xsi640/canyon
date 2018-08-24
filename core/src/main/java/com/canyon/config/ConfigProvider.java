package com.canyon.config;

import com.canyon.inject.Config;
import com.canyon.inject.InjectProvider;

public class ConfigProvider implements InjectProvider {
    @Override
    public boolean isMatch(Class<?> clazz) {
        return clazz.getAnnotation(Config.class) != null;
    }

    @Override
    public <T> T create(Class<T> objClazz, Class<T> fieldClazz) {
        Config c = fieldClazz.getAnnotation(Config.class);
        if (c.path().isEmpty()) {
            return ConfigFactory.load("application.conf", fieldClazz);
        } else {
            return ConfigFactory.load(c.path(), fieldClazz);
        }
    }
}
